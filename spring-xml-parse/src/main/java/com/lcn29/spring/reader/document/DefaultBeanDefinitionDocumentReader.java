package com.lcn29.spring.reader.document;

import com.lcn29.spring.bean.definition.holder.BeanDefinitionHolder;
import com.lcn29.spring.util.BeanDefinitionReaderUtils;
import com.lcn29.spring.util.StringUtils;
import com.lcn29.spring.xml.context.XmlReaderContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <pre>
 * Sax Document 读取器默认实现
 * </pre>
 *
 * @author lcn29
 * @date 2021-05-05 14:50
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {

    /**
     * xml 的 Resource 封装
     */
    private XmlReaderContext readerContext;

    /**
     * 真正的 BeanDefinition 解析器, xml 中的解析内容全部都在里面
     */
    private BeanDefinitionParserDelegate delegate;

    @Override
    public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) {
        this.readerContext = readerContext;
        Element root = doc.getDocumentElement();
        this.doRegisterBeanDefinitions(root);
    }

    /**
     * 开始解析 Document
     *
     * @param root 根节点
     */
    protected void doRegisterBeanDefinitions(Element root) {

        BeanDefinitionParserDelegate parent = this.delegate;
        this.delegate = createDelegate(getReaderContext(), root, parent);

        if (this.delegate.isDefaultNamespace(root)) {

            String profileSpec = root.getAttribute("profile");
            if (StringUtils.hasText(profileSpec)) {
                if (StringUtils.hasText(profileSpec)) {
                    String[] specifiedProfiles = StringUtils.tokenizeToStringArray(profileSpec, ",; ");
                    // TODO 当前配置的环境是否支持上面的 profile, 不支持直接返回
                }
            }
        }

        // 提供子类进行自定义, 在 xml 正式解析前进行预处理
        preProcessXml(root);
        parseBeanDefinitions(root, this.delegate);
        // 提供子类进行自定义, 在 xml 正式解析后进行处理
        postProcessXml(root);

        this.delegate = parent;
    }

    /**
     * 获取当前的 xmlReaderContext
     *
     * @return
     */
    protected final XmlReaderContext getReaderContext() {
        return this.readerContext;
    }

    /**
     * 获取真正的 BeanDefinition 解析器
     *
     * @param readerContext
     * @param root
     * @param parentDelegate
     * @return
     */
    protected BeanDefinitionParserDelegate createDelegate(XmlReaderContext readerContext, Element root, BeanDefinitionParserDelegate parentDelegate) {
        BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(readerContext);
        delegate.initDefaults(root, parentDelegate);
        return delegate;
    }

    /**
     * xml 解析前调用
     *
     * @param root
     */
    protected void preProcessXml(Element root) {
    }

    /**
     * xml 解析中
     *
     * @param root
     * @param delegate
     */
    protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {

        if (delegate.isDefaultNamespace(root)) {
            NodeList nl = root.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (node instanceof Element) {
                    Element ele = (Element) node;
                    if (delegate.isDefaultNamespace(ele)) {
                        parseDefaultElement(ele, delegate);
                    } else {
                        // 自定义标签的处理
                        delegate.parseCustomElement(ele);
                    }
                }
            }
        } else {
            // 自定义标签的处理
            delegate.parseCustomElement(root);
        }
    }

    /**
     * xml 解析完成后
     *
     * @param root
     */
    protected void postProcessXml(Element root) {
    }

    /**
     * 解析默认的标签
     *
     * @param ele
     * @param delegate
     */
    private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {

        if (delegate.nodeNameEquals(ele, "import")) {
            importBeanDefinitionResource(ele);
        } else if (delegate.nodeNameEquals(ele, "alias")) {
            processAliasRegistration(ele);
        } else if (delegate.nodeNameEquals(ele, "bean")) {
            processBeanDefinition(ele, delegate);
        } else if (delegate.nodeNameEquals(ele, "beans")) {
            // recurse
            doRegisterBeanDefinitions(ele);
        }
    }

    /**
     * import 标签的解析, 此处省略了
     *
     * @param ele
     */
    protected void importBeanDefinitionResource(Element ele) {
    }

    /**
     * alias 标签解析
     *
     * @param ele
     */
    protected void processAliasRegistration(Element ele) {

        String name = ele.getAttribute("name");
        String alias = ele.getAttribute("alias");
        boolean valid = true;

        if (!StringUtils.hasText(name)) {
            valid = false;
        }

        if (!StringUtils.hasText(alias)) {
            valid = false;
        }
        if (valid) {
            try {
                // 添加别名到容器中
                getReaderContext().getRegistry().registerAlias(name, alias);
            } catch (Exception ex) {
            }
        }
    }


    /**
     * bean 标签的解析
     *
     * @param ele
     * @param delegate
     */
    protected void processBeanDefinition(Element ele, BeanDefinitionParserDelegate delegate) {
        BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(ele);
        if (bdHolder != null) {
            // 如果有配置的话，对 beanDefinitionHolder 进行配置的装饰
            bdHolder = delegate.decorateBeanDefinitionIfRequired(ele, bdHolder);
            try {
                // Register the final decorated instance.
                BeanDefinitionReaderUtils.registerBeanDefinition(bdHolder, getReaderContext().getRegistry());
            } catch (RuntimeException ex) {
                // 异常解析处理
            }
        }
    }
}
