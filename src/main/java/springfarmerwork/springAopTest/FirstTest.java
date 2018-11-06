package springfarmerwork.springAopTest;

/**
 * https://blog.csdn.net/zhwyj1019/article/details/79510853
 * aop:入门第一步 AopNamespaceHandler ，解析器都是对BeanDefinitionParser接口的实现，所以入口都是parse()方法。
 * 首先注册： 注册AnnotationAutoProxyCreator
 * 注册或者升级
 * BeanDefinition beanDefinition = AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(parserContext.getRegistry(), parserContext.extractSource(sourceElement));
 * 对于proxy-target-class和expose-proxy属性的处理
 * useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
 * 注册组件并通知，便于监听器做进一步处理
 * registerComponentIfNecessary(beanDefinition, parserContext);
 * 对于AOP的实现基本上都是靠AnnotationAwareAspectJAutoProxyCreator去完成的
 *
 * https://blog.csdn.net/qq_27529917/article/details/78454830?locationNum=5&fps=1
 * 第二步：解析AnnotationAwareAspectJAutoProxyCreator
 */
public class FirstTest {
    /**
     * https://blog.csdn.net/zhwyj1019/article/details/79510853
     */
}
