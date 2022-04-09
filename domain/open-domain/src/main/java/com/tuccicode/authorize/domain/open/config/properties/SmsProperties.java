package com.tuccicode.authorize.domain.open.config.properties;

/**
 * @author tucci.lee
 */
public class SmsProperties {

    private String signName;
    private TemplateProperties template;

    public static class TemplateProperties{
        private String signin;
        private String modifyPhone;
        private String modifyPhoneVerify;
        private String modifyEmailVerify;
        private String modifyPassword;

        public String getSignin() {
            return signin;
        }

        public void setSignin(String signin) {
            this.signin = signin;
        }

        public String getModifyPhone() {
            return modifyPhone;
        }

        public void setModifyPhone(String modifyPhone) {
            this.modifyPhone = modifyPhone;
        }

        public String getModifyPhoneVerify() {
            return modifyPhoneVerify;
        }

        public void setModifyPhoneVerify(String modifyPhoneVerify) {
            this.modifyPhoneVerify = modifyPhoneVerify;
        }

        public String getModifyEmailVerify() {
            return modifyEmailVerify;
        }

        public void setModifyEmailVerify(String modifyEmailVerify) {
            this.modifyEmailVerify = modifyEmailVerify;
        }

        public String getModifyPassword() {
            return modifyPassword;
        }

        public void setModifyPassword(String modifyPassword) {
            this.modifyPassword = modifyPassword;
        }
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public TemplateProperties getTemplate() {
        return template;
    }

    public void setTemplate(TemplateProperties template) {
        this.template = template;
    }
}
