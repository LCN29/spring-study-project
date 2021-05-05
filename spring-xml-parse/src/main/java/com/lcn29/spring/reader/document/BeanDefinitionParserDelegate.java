package com.lcn29.spring.reader.document;

import com.lcn29.spring.bean.definition.AbstractBeanDefinition;
import com.lcn29.spring.bean.definition.BeanDefinition;
import com.lcn29.spring.bean.definition.attribute.BeanMetadataAttribute;
import com.lcn29.spring.bean.definition.attribute.PropertyValue;
import com.lcn29.spring.bean.definition.attribute.reference.RuntimeBeanNameReference;
import com.lcn29.spring.bean.definition.constructor.ConstructorArgumentValues;
import com.lcn29.spring.bean.definition.constructor.list.ManagedArray;
import com.lcn29.spring.bean.definition.constructor.reference.RuntimeBeanReference;
import com.lcn29.spring.bean.definition.constructor.value.TypedStringValue;
import com.lcn29.spring.bean.definition.defaults.DocumentDefaultsDefinition;
import com.lcn29.spring.bean.definition.holder.BeanDefinitionHolder;
import com.lcn29.spring.bean.definition.method.LookupOverride;
import com.lcn29.spring.bean.definition.method.MethodOverrides;
import com.lcn29.spring.bean.definition.method.ReplaceOverride;
import com.lcn29.spring.bean.definition.qualifier.AutowireCandidateQualifier;
import com.lcn29.spring.support.attribute.BeanMetadataAttributeAccessor;
import com.lcn29.spring.support.state.*;
import com.lcn29.spring.util.*;
import com.lcn29.spring.xml.NamespaceHandler;
import com.lcn29.spring.xml.context.XmlReaderContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * <pre>
 * BeanDefinition 解析器
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 15:15
 */
public class BeanDefinitionParserDelegate {

    /**
     * 默认支持的命名空间
     */
    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    /**
     * xml 的 Resource 包装
     */
    private final XmlReaderContext readerContext;

    /**
     * beanName 集合
     */
    private final Set<String> usedNames = new HashSet<>();

    /**
     * 默认的 xml 配置声明
     */
    private final DocumentDefaultsDefinition defaults = new DocumentDefaultsDefinition();

    /**
     * 当前的解析状态
     */
    private final ParseState parseState = new ParseState();

    public BeanDefinitionParserDelegate(XmlReaderContext readerContext) {
        this.readerContext = readerContext;
    }

    /**
     * 初始默认配置
     *
     * @param root
     * @param parent
     */
    public void initDefaults(Element root, BeanDefinitionParserDelegate parent) {
        // 默认配置的设置
        populateDefaults(this.defaults, (parent != null ? parent.defaults : null), root);
    }

    /**
     * 当前节点是否包含默认的命名空间
     *
     * @param node
     * @return
     */
    public boolean isDefaultNamespace(Node node) {
        return isDefaultNamespace(getNamespaceURI(node));
    }

    /**
     * 获取节点的命名空间 URI
     *
     * @param node
     * @return
     */
    public String getNamespaceURI(Node node) {
        return node.getNamespaceURI();
    }

    /**
     * 命名空间是否等于 http://www.springframework.org/schema/beans
     *
     * @param namespaceUri
     * @return
     */
    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    /**
     * 节点的标签名是否等于指定的名
     *
     * @param node        节点
     * @param desiredName 指定的名称
     * @return
     */
    public boolean nodeNameEquals(Node node, String desiredName) {
        return desiredName.equals(node.getNodeName()) || desiredName.equals(getLocalName(node));
    }

    /**
     * 获取标签的名称
     *
     * @param node 标签
     * @return
     */
    public String getLocalName(Node node) {
        return node.getLocalName();
    }

    /**
     * 检查 beanName 是否存在
     *
     * @param beanName
     * @param aliases
     * @param beanElement
     */
    public void checkNameUniqueness(String beanName, List<String> aliases, Element beanElement) {

        String foundName = null;

        if (StringUtils.hasText(beanName) && this.usedNames.contains(beanName)) {
            foundName = beanName;
        }
        if (foundName == null) {
            foundName = CollectionUtils.findFirstMatch(this.usedNames, aliases);
        }
        if (foundName != null) {
            error("Bean name '" + foundName + "' is already used in this <beans> element", beanElement);
        }

        this.usedNames.add(beanName);
        this.usedNames.addAll(aliases);
    }

    /**
     * 解析标签
     *
     * @param ele 标签
     * @return
     */
    public BeanDefinitionHolder parseBeanDefinitionElement(Element ele) {
        return parseBeanDefinitionElement(ele, null);
    }

    /**
     * 解析标签
     *
     * @param ele            标签
     * @param containingBean beanDefinition
     * @return
     */
    public BeanDefinitionHolder parseBeanDefinitionElement(Element ele, BeanDefinition containingBean) {
        String id = ele.getAttribute("id");
        String nameAttr = ele.getAttribute("name");


        List<String> aliases = new ArrayList<>();
        if (StringUtils.hasLength(nameAttr)) {
            String[] nameArr = StringUtils.tokenizeToStringArray(nameAttr, ",; ");
            aliases.addAll(Arrays.asList(nameArr));
        }

        // 有别名, 取第一个作为 beanName
        String beanName = id;
        if (!StringUtils.hasText(beanName) && !aliases.isEmpty()) {
            beanName = aliases.remove(0);
        }

        if (containingBean == null) {
            checkNameUniqueness(beanName, aliases, ele);
        }

        AbstractBeanDefinition beanDefinition = parseBeanDefinitionElement(ele, beanName, containingBean);

        if (beanDefinition != null) {
            if (!StringUtils.hasText(beanName)) {
                try {
                    if (containingBean != null) {
                        beanName = BeanDefinitionReaderUtils.generateBeanName(beanDefinition, this.readerContext.getRegistry(), true);
                    } else {
                        // 没有 beanName, 生成一个
                        beanName = this.readerContext.generateBeanName(beanDefinition);
                        String beanClassName = beanDefinition.getBeanClassName();
                        if (beanClassName != null && beanName.startsWith(beanClassName) && beanName.length() > beanClassName.length() &&
                                !this.readerContext.getRegistry().isBeanNameInUse(beanClassName)) {
                            aliases.add(beanClassName);
                        }
                    }
                } catch (Exception ex) {
                    error(ex.getMessage(), ele);
                    return null;
                }
            }

            String[] aliasesArray = StringUtils.toStringArray(aliases);
            return new BeanDefinitionHolder(beanDefinition, beanName, aliasesArray);
        }

        return null;
    }

    /**
     * @param ele            解析标签
     * @param beanName       bean 名
     * @param containingBean beanDefinition
     * @return
     */
    public AbstractBeanDefinition parseBeanDefinitionElement(Element ele, String beanName, BeanDefinition containingBean) {

        this.parseState.push(new BeanEntry(beanName));
        String className = null;

        if (ele.hasAttribute("class")) {
            className = ele.getAttribute("class").trim();
        }

        String parent = null;
        if (ele.hasAttribute("parent")) {
            parent = ele.getAttribute("parent");
        }

        try {
            AbstractBeanDefinition bd = createBeanDefinition(className, parent);

            parseBeanDefinitionAttributes(ele, beanName, containingBean, bd);
            bd.setDescription(DomUtils.getChildElementValueByTagName(ele, "description"));

            parseMetaElements(ele, bd);
            parseLookupOverrideSubElements(ele, bd.getMethodOverrides());
            parseReplacedMethodSubElements(ele, bd.getMethodOverrides());

            parseConstructorArgElements(ele, bd);
            parsePropertyElements(ele, bd);
            parseQualifierElements(ele, bd);

            bd.setResource(this.readerContext.getResource());
            bd.setSource(extractSource(ele));

            return bd;
        } catch (Exception ex) {
        } finally {
            this.parseState.pop();
        }

        return null;
    }

    /**
     * 属性解析
     *
     * @param ele            标签
     * @param beanName       bean 名
     * @param containingBean beanDefinition
     * @param bd             AbstractBeanDefinition
     */
    public void parseBeanDefinitionAttributes(Element ele, String beanName, BeanDefinition containingBean, AbstractBeanDefinition bd) {

        if (ele.hasAttribute("singleton")) {
            error("Old 1.x 'singleton' attribute in use - upgrade to 'scope' declaration", ele);

        } else if (ele.hasAttribute("scope")) {
            bd.setScope(ele.getAttribute("scope"));

        } else if (containingBean != null) {
            bd.setScope(containingBean.getScope());
        }

        if (ele.hasAttribute("abstract")) {
            bd.setAbstract("true".equals(ele.getAttribute("abstract")));
        }

        String lazyInit = ele.getAttribute("lazy-init");
        if (isDefaultValue(lazyInit)) {
            lazyInit = this.defaults.getLazyInit();
        }
        bd.setLazyInit("true".equals(lazyInit));

        // 其他的 autowire, depends-on, autowire-candidate, primary, init-method, destroy-method, factory-method, factory-bean
        // 类似的进行省略了
    }

    /**
     * 解析标签上的 meta
     *
     * @param ele
     * @param attributeAccessor
     */
    public void parseMetaElements(Element ele, BeanMetadataAttributeAccessor attributeAccessor) {
        NodeList nl = ele.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (isCandidateElement(node) && nodeNameEquals(node, "meta")) {
                Element metaElement = (Element) node;
                String key = metaElement.getAttribute("key");
                String value = metaElement.getAttribute("value");
                BeanMetadataAttribute attribute = new BeanMetadataAttribute(key, value);
                attribute.setSource(extractSource(metaElement));
                attributeAccessor.addMetadataAttribute(attribute);
            }
        }
    }

    /**
     * 解析标签的 lookup-method 节点
     * <p>
     * 使用: https://blog.csdn.net/Kevin_NZF/article/details/104142810
     *
     * @param beanEle
     * @param overrides
     */
    public void parseLookupOverrideSubElements(Element beanEle, MethodOverrides overrides) {
        NodeList nl = beanEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (isCandidateElement(node) && nodeNameEquals(node, "lookup-method")) {
                Element ele = (Element) node;
                String methodName = ele.getAttribute("name");
                String beanRef = ele.getAttribute("bean");
                LookupOverride override = new LookupOverride(methodName, beanRef);
                override.setSource(extractSource(ele));
                overrides.addOverride(override);
            }
        }
    }

    /**
     * 解析标签的 replaced-method 节点
     * <p>
     * 使用: https://my.oschina.net/u/1590001/blog/268212
     *
     * @param beanEle
     * @param overrides
     */
    public void parseReplacedMethodSubElements(Element beanEle, MethodOverrides overrides) {
        NodeList nl = beanEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (isCandidateElement(node) && nodeNameEquals(node, "replaced-method")) {
                Element replacedMethodEle = (Element) node;
                String name = replacedMethodEle.getAttribute("name");
                String callback = replacedMethodEle.getAttribute("replacer");
                ReplaceOverride replaceOverride = new ReplaceOverride(name, callback);
                // Look for arg-type match elements.
                List<Element> argTypeEles = DomUtils.getChildElementsByTagName(replacedMethodEle, "arg-type");
                for (Element argTypeEle : argTypeEles) {
                    String match = argTypeEle.getAttribute("match");
                    match = (StringUtils.hasText(match) ? match : DomUtils.getTextValue(argTypeEle));
                    if (StringUtils.hasText(match)) {
                        replaceOverride.addTypeIdentifier(match);
                    }
                }
                replaceOverride.setSource(extractSource(replacedMethodEle));
                overrides.addOverride(replaceOverride);
            }
        }
    }

    /**
     * 构造函数参数解析
     *
     * @param beanEle
     * @param bd
     */
    public void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        NodeList nl = beanEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (isCandidateElement(node) && nodeNameEquals(node, "constructor-arg")) {
                parseConstructorArgElement((Element) node, bd);
            }
        }
    }

    /**
     * 解析构造函数参数
     *
     * @param ele
     * @param bd
     */
    public void parseConstructorArgElement(Element ele, BeanDefinition bd) {

        String indexAttr = ele.getAttribute("index");
        String typeAttr = ele.getAttribute("type");
        String nameAttr = ele.getAttribute("name");


        // 有位置的参数处理
        if (StringUtils.hasLength(indexAttr)) {

            try {
                int index = Integer.parseInt(indexAttr);
                if (index < 0) {
                    error("'index' cannot be lower than 0", ele);
                } else {
                    try {
                        this.parseState.push(new ConstructorArgumentEntry(index));
                        Object value = parsePropertyValue(ele, bd, null);
                        ConstructorArgumentValues.ValueHolder valueHolder = new ConstructorArgumentValues.ValueHolder(value);
                        if (StringUtils.hasLength(typeAttr)) {
                            valueHolder.setType(typeAttr);
                        }
                        if (StringUtils.hasLength(nameAttr)) {
                            valueHolder.setName(nameAttr);
                        }
                        valueHolder.setSource(extractSource(ele));
                        if (bd.getConstructorArgumentValues().hasIndexedArgumentValue(index)) {
                            error("Ambiguous constructor-arg entries for index " + index, ele);
                        } else {
                            bd.getConstructorArgumentValues().addIndexedArgumentValue(index, valueHolder);
                        }
                    } finally {
                        this.parseState.pop();
                    }
                }

            } catch (NumberFormatException ex) {
                error("Attribute 'index' of tag 'constructor-arg' must be an integer", ele);
            }
            return;
        }


        // 无位置的参数的处理

        try {
            this.parseState.push(new ConstructorArgumentEntry());
            Object value = parsePropertyValue(ele, bd, null);
            ConstructorArgumentValues.ValueHolder valueHolder = new ConstructorArgumentValues.ValueHolder(value);
            if (StringUtils.hasLength(typeAttr)) {
                valueHolder.setType(typeAttr);
            }
            if (StringUtils.hasLength(nameAttr)) {
                valueHolder.setName(nameAttr);
            }
            valueHolder.setSource(extractSource(ele));
            bd.getConstructorArgumentValues().addGenericArgumentValue(valueHolder);
        } finally {
            this.parseState.pop();
        }
    }

    /**
     * 解析参数属性值
     *
     * @param ele
     * @param bd
     * @param propertyName
     * @return
     */
    public Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {

        String elementName = (propertyName != null ? "<property> element for property '" + propertyName + "'" : "<constructor-arg> element");

        // 只能有一个元素 ref, value, list 等中的一个
        NodeList nl = ele.getChildNodes();
        Element subElement = null;
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element && !nodeNameEquals(node, "description") && !nodeNameEquals(node, "meta")) {
                // Child element is what we're looking for.
                if (subElement != null) {
                    error(elementName + " must not contain more than one sub-element", ele);
                } else {
                    subElement = (Element) node;
                }
            }
        }

        boolean hasRefAttribute = ele.hasAttribute("ref");
        boolean hasValueAttribute = ele.hasAttribute("value");
        if ((hasRefAttribute && hasValueAttribute) || ((hasRefAttribute || hasValueAttribute) && subElement != null)) {
            error(elementName + " is only allowed to contain either 'ref' attribute OR 'value' attribute OR sub-element", ele);
        }

        if (hasRefAttribute) {

            // ref 的处理
            String refName = ele.getAttribute("ref");
            if (!StringUtils.hasText(refName)) {
                error(elementName + " contains empty 'ref' attribute", ele);
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            ref.setSource(extractSource(ele));
            return ref;

        } else if (hasValueAttribute) {

            // value 的处理
            TypedStringValue valueHolder = new TypedStringValue(ele.getAttribute("value"));
            valueHolder.setSource(extractSource(ele));
            return valueHolder;

        } else if (subElement != null) {

            // list, set 等集合的处理

            return parsePropertySubElement(subElement, bd);
        } else {
            // Neither child element nor "ref" or "value" attribute found.
            error(elementName + " must specify a ref or value", ele);
            return null;
        }
    }

    /**
     * 解析构造函数参数子元素
     *
     * @param ele
     * @param bd
     * @return
     */
    public Object parsePropertySubElement(Element ele, BeanDefinition bd) {
        return parsePropertySubElement(ele, bd, null);
    }

    /**
     * 解析构造函数参数子元素
     *
     * @param ele
     * @param bd
     * @param defaultValueType 默认的 value 类型
     * @return
     */
    public Object parsePropertySubElement(Element ele, BeanDefinition bd, String defaultValueType) {

        // 自定义标签解析
        if (!isDefaultNamespace(ele)) {
            return parseNestedCustomElement(ele, bd);
        }

        // bean 标签直接解析
        if (nodeNameEquals(ele, "bean")) {
            BeanDefinitionHolder nestedBd = parseBeanDefinitionElement(ele, bd);
            if (nestedBd != null) {
                nestedBd = decorateBeanDefinitionIfRequired(ele, nestedBd, bd);
            }
            return nestedBd;
        }

        // ref 属性解析
        if (nodeNameEquals(ele, "ref")) {
            String refName = ele.getAttribute("bean");
            boolean toParent = false;
            if (!StringUtils.hasLength(refName)) {

                refName = ele.getAttribute("parent");
                toParent = true;
                if (!StringUtils.hasLength(refName)) {
                    error("'bean' or 'parent' is required for <ref> element", ele);
                    return null;
                }
            }
            if (!StringUtils.hasText(refName)) {
                error("<ref> element contains empty target attribute", ele);
                return null;
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName, toParent);
            ref.setSource(extractSource(ele));
            return ref;
        }

        // idref 属性解析
        if (nodeNameEquals(ele, "idref")) {
            return parseIdRefElement(ele);
        }

        // value 属性解析
        if (nodeNameEquals(ele, "value")) {
            return parseValueElement(ele, defaultValueType);
        }

        // null 属性解析
        if (nodeNameEquals(ele, "null")) {
            TypedStringValue nullHolder = new TypedStringValue(null);
            nullHolder.setSource(extractSource(ele));
            return nullHolder;
        }

        // array 属性解析
        if (nodeNameEquals(ele, "array")) {
            return parseArrayElement(ele, bd);
        }

        // 下面的 list, set, map, props 的解析和 array 类似, 进行省略了

        // list 属性解析
        if (nodeNameEquals(ele, "list")) {
            // return parseListElement(ele, bd);
        }

        // set 属性解析
        if (nodeNameEquals(ele, "set")) {
            // return parseSetElement(ele, bd);
        }

        // map 属性解析
        if (nodeNameEquals(ele, "map")) {
            // return parseMapElement(ele, bd);
        }

        // props 属性解析
        if (nodeNameEquals(ele, "props")) {
            // return parsePropsElement(ele);
        }

        // 其他的不解析了
        error("Unknown property sub-element: [" + ele.getNodeName() + "]", ele);
        return null;

    }

    /**
     * 自定义标签解析
     *
     * @param ele
     * @return
     */
    public BeanDefinition parseCustomElement(Element ele) {
        return parseCustomElement(ele, null);
    }

    /**
     * 自定义标签的解析
     *
     * @param ele
     * @param containingBd
     * @return
     */
    public BeanDefinition parseCustomElement(Element ele, BeanDefinition containingBd) {
        String namespaceUri = getNamespaceURI(ele);
        if (namespaceUri == null) {
            return null;
        }
        NamespaceHandler handler = this.readerContext.getNamespaceHandlerResolver().resolve(namespaceUri);
        if (handler == null) {
            error("Unable to locate Spring NamespaceHandler for XML schema namespace [" + namespaceUri + "]", ele);
            return null;
        }
        return handler.parse(ele, new ParserContext(this.readerContext, this, containingBd));
    }

    /**
     * idref 解析
     *
     * @param ele
     * @return
     */
    public Object parseIdRefElement(Element ele) {

        String refName = ele.getAttribute("bean");
        if (!StringUtils.hasLength(refName)) {
            error("'bean' is required for <idref> element", ele);
            return null;
        }
        if (!StringUtils.hasText(refName)) {
            error("<idref> element contains empty target attribute", ele);
            return null;
        }
        RuntimeBeanNameReference ref = new RuntimeBeanNameReference(refName);
        ref.setSource(extractSource(ele));
        return ref;
    }

    /**
     * value 解析
     *
     * @param ele
     * @param defaultTypeName
     * @return
     */
    public Object parseValueElement(Element ele, String defaultTypeName) {

        String value = DomUtils.getTextValue(ele);
        String specifiedTypeName = ele.getAttribute("type");
        String typeName = specifiedTypeName;
        if (!StringUtils.hasText(typeName)) {
            typeName = defaultTypeName;
        }
        try {
            TypedStringValue typedValue = buildTypedStringValue(value, typeName);
            typedValue.setSource(extractSource(ele));
            typedValue.setSpecifiedTypeName(specifiedTypeName);
            return typedValue;
        } catch (ClassNotFoundException ex) {
            error("Type class [" + typeName + "] not found for <value> element", ele);
            return value;
        }
    }

    /**
     * array 解析
     *
     * @param arrayEle
     * @param bd
     * @return
     */
    public Object parseArrayElement(Element arrayEle, BeanDefinition bd) {
        String elementType = arrayEle.getAttribute("value-type");
        NodeList nl = arrayEle.getChildNodes();
        ManagedArray target = new ManagedArray(elementType, nl.getLength());
        target.setSource(extractSource(arrayEle));
        target.setElementTypeName(elementType);
        target.setMergeEnabled(parseMergeAttribute(arrayEle));
        parseCollectionElements(nl, target, bd, elementType);
        return target;
    }

    /**
     * 判断元素是否可合并
     *
     * @param collectionElement
     * @return
     */
    public boolean parseMergeAttribute(Element collectionElement) {
        String value = collectionElement.getAttribute("merge");
        if (isDefaultValue(value)) {
            value = this.defaults.getMerge();
        }
        return "true".equals(value);
    }

    /**
     * 节点解析
     *
     * @param elementNodes       节点列表
     * @param target             可合并 Collection
     * @param bd                 beanDefinition
     * @param defaultElementType value 的类型
     */
    protected void parseCollectionElements(NodeList elementNodes, Collection<Object> target, BeanDefinition bd, String defaultElementType) {

        for (int i = 0; i < elementNodes.getLength(); i++) {
            Node node = elementNodes.item(i);
            if (node instanceof Element && !nodeNameEquals(node, "description")) {
                target.add(parsePropertySubElement((Element) node, bd, defaultElementType));
            }
        }
    }

    /**
     * 属性标签解析
     *
     * @param beanEle
     * @param bd
     */
    public void parsePropertyElements(Element beanEle, BeanDefinition bd) {
        NodeList nl = beanEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (isCandidateElement(node) && nodeNameEquals(node, "property")) {
                parsePropertyElement((Element) node, bd);
            }
        }
    }

    /**
     * 属性标签解析
     *
     * @param ele
     * @param bd
     */
    public void parsePropertyElement(Element ele, BeanDefinition bd) {
        String propertyName = ele.getAttribute("name");
        if (!StringUtils.hasLength(propertyName)) {
            error("Tag 'property' must have a 'name' attribute", ele);
            return;
        }
        this.parseState.push(new PropertyEntry(propertyName));
        try {
            if (bd.getPropertyValues().contains(propertyName)) {
                error("Multiple 'property' definitions for property '" + propertyName + "'", ele);
                return;
            }
            Object val = parsePropertyValue(ele, bd, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);
            parseMetaElements(ele, pv);
            pv.setSource(extractSource(ele));
            bd.getPropertyValues().addPropertyValue(pv);
        } finally {
            this.parseState.pop();
        }
    }

    /**
     * qualifier 标签解析
     *
     * @param beanEle
     * @param bd
     */
    public void parseQualifierElements(Element beanEle, AbstractBeanDefinition bd) {
        NodeList nl = beanEle.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (isCandidateElement(node) && nodeNameEquals(node, "qualifier")) {
                parseQualifierElement((Element) node, bd);
            }
        }
    }

    /**
     * qualifier 标签解析
     *
     * @param ele
     * @param bd
     */
    public void parseQualifierElement(Element ele, AbstractBeanDefinition bd) {
        String typeName = ele.getAttribute("type");
        if (!StringUtils.hasLength(typeName)) {
            error("Tag 'qualifier' must have a 'type' attribute", ele);
            return;
        }
        this.parseState.push(new QualifierEntry(typeName));
        try {
            AutowireCandidateQualifier qualifier = new AutowireCandidateQualifier(typeName);
            qualifier.setSource(extractSource(ele));
            String value = ele.getAttribute("value");
            if (StringUtils.hasLength(value)) {
                qualifier.setAttribute("value", value);
            }
            NodeList nl = ele.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (isCandidateElement(node) && nodeNameEquals(node, "attribute")) {
                    Element attributeEle = (Element) node;
                    String attributeName = attributeEle.getAttribute("key");
                    String attributeValue = attributeEle.getAttribute("value");
                    if (StringUtils.hasLength(attributeName) && StringUtils.hasLength(attributeValue)) {
                        BeanMetadataAttribute attribute = new BeanMetadataAttribute(attributeName, attributeValue);
                        attribute.setSource(extractSource(attributeEle));
                        qualifier.addMetadataAttribute(attribute);
                    } else {
                        error("Qualifier 'attribute' tag must have a 'name' and 'value'", attributeEle);
                        return;
                    }
                }
            }
            bd.addQualifier(qualifier);
        } finally {
            this.parseState.pop();
        }
    }

    /**
     * 如果有，进行 beanDefinitionHolder 进行装饰
     *
     * @param ele         标签
     * @param originalDef beanDefinitionHolder
     * @return
     */
    public BeanDefinitionHolder decorateBeanDefinitionIfRequired(Element ele, BeanDefinitionHolder originalDef) {
        return decorateBeanDefinitionIfRequired(ele, originalDef, null);
    }

    /**
     * 如果有，进行 beanDefinitionHolder 进行装饰
     *
     * @param ele          标签
     * @param originalDef  beanDefinitionHolder
     * @param containingBd beanDefinition
     * @return
     */
    public BeanDefinitionHolder decorateBeanDefinitionIfRequired(Element ele, BeanDefinitionHolder originalDef, BeanDefinition containingBd) {

        BeanDefinitionHolder finalDefinition = originalDef;

        NamedNodeMap attributes = ele.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node node = attributes.item(i);
            finalDefinition = decorateIfRequired(node, finalDefinition, containingBd);
        }

        // Decorate based on custom nested elements.
        NodeList children = ele.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                finalDefinition = decorateIfRequired(node, finalDefinition, containingBd);
            }
        }
        return finalDefinition;
    }

    /**
     * 如果有，进行 beanDefinitionHolder 进行装饰
     *
     * @param node         节点
     * @param originalDef  beanDefinitionHolder
     * @param containingBd beanDefinition
     * @return
     */
    public BeanDefinitionHolder decorateIfRequired(Node node, BeanDefinitionHolder originalDef, BeanDefinition containingBd) {

        String namespaceUri = getNamespaceURI(node);
        if (namespaceUri != null && !isDefaultNamespace(namespaceUri)) {
            NamespaceHandler handler = this.readerContext.getNamespaceHandlerResolver().resolve(namespaceUri);
            if (handler != null) {
                BeanDefinitionHolder decorated = handler.decorate(node, originalDef, new ParserContext(this.readerContext, this, containingBd));
                if (decorated != null) {
                    return decorated;
                }
            } else if (namespaceUri.startsWith("http://www.springframework.org/")) {
                error("Unable to locate Spring NamespaceHandler for XML schema namespace [" + namespaceUri + "]", null);
            } else {
                // 打印日志
            }
        }
        return originalDef;
    }

    /**
     * 默认值初始化
     *
     * @param defaults
     * @param parentDefaults
     * @param root
     */
    protected void populateDefaults(DocumentDefaultsDefinition defaults, DocumentDefaultsDefinition parentDefaults, Element root) {

        String lazyInit = root.getAttribute("default-lazy-init");

        if (isDefaultValue(lazyInit)) {
            lazyInit = (parentDefaults != null ? parentDefaults.getLazyInit() : "false");
        }
        defaults.setLazyInit(lazyInit);

        // 下面的 default-merge, default-autowire, default-autowire-candidates, default-init-method, default-destroy-method
        // 逻辑差不多, 省略了

        defaults.setSource(this.readerContext.extractSource(root));
    }

    /**
     * 创建一个 BeanDefinition 的实现
     *
     * @param className
     * @param parentName
     * @return
     * @throws ClassNotFoundException
     */
    protected AbstractBeanDefinition createBeanDefinition(String className, String parentName) throws ClassNotFoundException {
        return BeanDefinitionReaderUtils.createBeanDefinition(parentName, className, this.readerContext.getBeanClassLoader());
    }

    /**
     * 获取元数据的源
     *
     * @param ele
     * @return
     */
    protected Object extractSource(Element ele) {
        return this.readerContext.extractSource(ele);
    }

    /**
     * 获取 TypedStringValue
     *
     * @param value
     * @param targetTypeName
     * @return
     * @throws ClassNotFoundException
     */
    protected TypedStringValue buildTypedStringValue(String value, String targetTypeName) throws ClassNotFoundException {

        ClassLoader classLoader = this.readerContext.getBeanClassLoader();
        TypedStringValue typedValue;
        if (!StringUtils.hasText(targetTypeName)) {
            typedValue = new TypedStringValue(value);
        } else if (classLoader != null) {
            Class<?> targetType = ClassUtils.forName(targetTypeName, classLoader);
            typedValue = new TypedStringValue(value, targetType);
        } else {
            typedValue = new TypedStringValue(value, targetTypeName);
        }
        return typedValue;
    }

    /**
     * 默认值判断
     *
     * @param value 配置的值
     * @return
     */
    private boolean isDefaultValue(String value) {
        return ("default".equals(value) || "".equals(value));
    }

    /**
     * 异常事件通知
     *
     * @param message 消息
     * @param source  标签
     */
    private void error(String message, Element source) {
        // 在 Spring 中此处是发送一个事件通知, 消费端收到消息后没做什么处理, 所以此处直接打印了个日志
        System.err.println(message);
    }

    /**
     * 节点为默认命名空间中的标签节点
     *
     * @param node 节点
     * @return
     */
    private boolean isCandidateElement(Node node) {
        return (node instanceof Element && (isDefaultNamespace(node) || !isDefaultNamespace(node.getParentNode())));
    }

    /**
     * 解析嵌套的自定义标签
     *
     * @param ele
     * @param containingBd
     * @return
     */
    private BeanDefinitionHolder parseNestedCustomElement(Element ele, BeanDefinition containingBd) {
        BeanDefinition innerDefinition = parseCustomElement(ele, containingBd);
        if (innerDefinition == null) {
            error("Incorrect usage of element '" + ele.getNodeName() + "' in a nested manner. This tag cannot be used nested inside <property>.", ele);
            return null;
        }
        String id = ele.getNodeName() + "#" + ObjectUtils.getIdentityHexString(innerDefinition);
        return new BeanDefinitionHolder(innerDefinition, id);
    }
}
