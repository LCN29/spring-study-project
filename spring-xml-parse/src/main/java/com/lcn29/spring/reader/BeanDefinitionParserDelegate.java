package com.lcn29.spring.reader;

import com.lcn29.spring.bean.BeanDefinition;
import com.lcn29.spring.bean.BeanDefinitionHolder;
import com.lcn29.spring.util.CollectionUtils;
import com.lcn29.spring.util.StringUtils;
import com.lcn29.spring.bean.AbstractBeanDefinition;
import com.lcn29.spring.util.BeanDefinitionReaderUtils;
import com.sun.istack.internal.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author lcn29
 * @date 2021-04-28 14:26
 */
public class BeanDefinitionParserDelegate {

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    private final XmlReaderContext readerContext;

    private final Set<String> usedNames = new HashSet<>();

    private final DocumentDefaultsDefinition defaults = new DocumentDefaultsDefinition();

    public BeanDefinitionParserDelegate(XmlReaderContext readerContext) {
        this.readerContext = readerContext;
    }

    public void initDefaults(Element root, BeanDefinitionParserDelegate parent) {

        // 默认配置的设置
        populateDefaults(this.defaults, (parent != null ? parent.defaults : null), root);
        // 注册事件
        //this.readerContext.fireDefaultsRegistered(this.defaults);
    }

    protected void populateDefaults(DocumentDefaultsDefinition defaults, DocumentDefaultsDefinition parentDefaults, Element root) {
        String lazyInit = root.getAttribute("default-lazy-init");

        if (isDefaultValue(lazyInit)) {
            // Potentially inherited from outer <beans> sections, otherwise falling back to false.
            lazyInit = (parentDefaults != null ? parentDefaults.getLazyInit() : "false");
        }
        defaults.setLazyInit(lazyInit);

        String merge = root.getAttribute("default-merge");
        if (isDefaultValue(merge)) {
            // Potentially inherited from outer <beans> sections, otherwise falling back to false.
            merge = (parentDefaults != null ? parentDefaults.getMerge() : "false");
        }
        defaults.setMerge(merge);

        String autowire = root.getAttribute("default-autowire");
        if (isDefaultValue(autowire)) {
            // Potentially inherited from outer <beans> sections, otherwise falling back to 'no'.
            autowire = (parentDefaults != null ? parentDefaults.getAutowire() : "no");
        }
        defaults.setAutowire(autowire);

        if (root.hasAttribute("default-autowire-candidates")) {
            defaults.setAutowireCandidates(root.getAttribute("default-autowire-candidates"));
        } else if (parentDefaults != null) {
            defaults.setAutowireCandidates(parentDefaults.getAutowireCandidates());
        }

        if (root.hasAttribute("default-init-method")) {
            defaults.setInitMethod(root.getAttribute("default-init-method"));
        } else if (parentDefaults != null) {
            defaults.setInitMethod(parentDefaults.getInitMethod());
        }

        if (root.hasAttribute("default-destroy-method")) {
            defaults.setDestroyMethod(root.getAttribute("default-destroy-method"));
        } else if (parentDefaults != null) {
            defaults.setDestroyMethod(parentDefaults.getDestroyMethod());
        }

        defaults.setSource(this.readerContext.extractSource(root));

    }

    private boolean isDefaultValue(String value) {
        return ("default".equals(value) || "".equals(value));
    }

    public boolean isDefaultNamespace(Node node) {
        return isDefaultNamespace(getNamespaceURI(node));
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    public String getNamespaceURI(Node node) {
        return node.getNamespaceURI();
    }

    public boolean nodeNameEquals(Node node, String desiredName) {
        return desiredName.equals(node.getNodeName()) || desiredName.equals(getLocalName(node));
    }

    public String getLocalName(Node node) {
        return node.getLocalName();
    }

    public BeanDefinitionHolder parseBeanDefinitionElement(Element ele) {
        return parseBeanDefinitionElement(ele, null);
    }

    public BeanDefinitionHolder parseBeanDefinitionElement(Element ele, BeanDefinition containingBean) {
        String id = ele.getAttribute("id");
        String nameAttr = ele.getAttribute("name");

        List<String> aliases = new ArrayList<>();
        if (StringUtils.hasLength(nameAttr)) {
            String[] nameArr = StringUtils.tokenizeToStringArray(nameAttr, ",; ");
            aliases.addAll(Arrays.asList(nameArr));
        }

        String beanName = id;
        if (!StringUtils.hasText(beanName) && !aliases.isEmpty()) {
            beanName = aliases.remove(0);
        }

        if (containingBean == null) {
            checkNameUniqueness(beanName, aliases, ele);
        }

        AbstractBeanDefinition beanDefinition = parseBeanDefinitionElement(ele, beanName, containingBean);


    }

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


        } catch (Exception ex) {

        } finally {
            this.parseState.pop();
        }

    }

    public BeanDefinitionHolder decorateBeanDefinitionIfRequired(Element ele, BeanDefinitionHolder bdHolder) {
    }

    protected void checkNameUniqueness(String beanName, List<String> aliases, Element beanElement) {
        String foundName = null;

        if (StringUtils.hasText(beanName) && this.usedNames.contains(beanName)) {
            foundName = beanName;
        }
        if (foundName == null) {
            foundName = CollectionUtils.findFirstMatch(this.usedNames, aliases);
        }
        if (foundName != null) {
        }

        this.usedNames.add(beanName);
        this.usedNames.addAll(aliases);
    }

    protected AbstractBeanDefinition createBeanDefinition(@Nullable String className, @Nullable String parentName)
            throws ClassNotFoundException {

        return BeanDefinitionReaderUtils.createBeanDefinition(parentName, className, this.readerContext.getBeanClassLoader());
    }


}
