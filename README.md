[线上地址](https://passport.2cci.cn) \
web-authorize服务同时支持app和web用户，但是必须通过cookie的方式认证。\
如果即想有cookie认证又有header的认证，可以添加一个Authenticator，参考DefaultAuthenticator。 

登录会set cookie，并且会携带token返回。\
web端不需要操作，app端需要将返回的token保存，请求时携带token在cookie中 

由于前后端分离，不想将编译好的前端代码放到java代码中，所以sso重定向在前端实现，但是这样会有用户体验问题。\
当然也可以将编译好的前端代码放到java代码中，提高用户体验