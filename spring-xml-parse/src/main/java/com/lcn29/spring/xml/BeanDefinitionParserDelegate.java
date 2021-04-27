package com.lcn29.spring.xml;

import com.lcn29.spring.bean.AbstractBeanDefinition;
import com.lcn29.spring.bean.BeanDefinition;
import com.lcn29.spring.bean.BeanDefinitionHolder;
import com.lcn29.spring.util.BeanDefinitionReaderUtils;
import com.lcn29.spring.util.CollectionUtils;
import com.lcn29.spring.util.StringUtils;
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
 * @date 2021-04-27 17:47
 */
public class BeanDefinitionParserDelegate {

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    private final XmlReaderContext readerContext;

    private final DocumentDefaultsDefinition defaults = new DocumentDefaultsDefinition();

    private final Set<String> usedNames = new HashSet<>();

    public BeanDefinitionParserDelegate(XmlReaderContext readerContext) {
        this.readerContext = readerContext;
    }

    public void initDefaults(Element root, BeanDefinitionParserDelegate parent) {
        populateDefaults(this.defaults, (parent != null ? parent.defaults : null), root);
        this.readerContext.fireDefaultsRegistered(this.defaults);
    }

    protected void populateDefaults(DocumentDefaultsDefinition defaults, @Nullable DocumentDefaultsDefinition parentDefaults, Element root) {
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
        }
        else if (parentDefaults != null) {
            defaults.setAutowireCandidates(parentDefaults.getAutowireCandidates());
        }

        if (root.hasAttribute("default-init-method")) {
            defaults.setInitMethod(root.getAttribute("default-init-method"));
        }
        else if (parentDefaults != null) {
            defaults.setInitMethod(parentDefaults.getInitMethod());
        }

        if (root.hasAttribute("default-destroy-method")) {
            defaults.setDestroyMethod(root.getAttribute("default-destroy-method"));
        }
        else if (parentDefaults != null) {
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

    public boolean isDefaultNamespace(@Nullable String namespaceUri) {
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

    public BeanDefinitionHolder parseBeanDefinitionElement(Element ele, @Nullable BeanDefinition containingBean) {
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
        if (beanDefinition != null) {
            if (!StringUtils.hasText(beanName)) {
                try {
                    if (containingBean != null) {
                        beanName = BeanDefinitionReaderUtils.generateBeanName(
                                beanDefinition, this.readerContext.getRegistry(), true);
                    }
                    else {
                        beanName = this.readerContext.generateBeanName(beanDefinition);
                        // Register an alias for the plain bean class name, if still possible,
                        // if the generator returned the class name plus a suffix.
                        // This is expected for Spring 1.2/2.0 backwards compatibility.
                        String beanClassName = beanDefinition.getBeanClassName();
                        if (beanClassName != null &&
                                beanName.startsWith(beanClassName) && beanName.length() > beanClassName.length() &&
                                !this.readerContext.getRegistry().isBeanNameInUse(beanClassName)) {
                            aliases.add(beanClassName);
                        }
                    }
                }
                catch (Exception ex) {

                    return null;
                }
            }
            String[] aliasesArray = StringUtils.toStringArray(aliases);
            return new BeanDefinitionHolder(beanDefinition, beanName, aliasesArray);
        }

        return null;
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



}
