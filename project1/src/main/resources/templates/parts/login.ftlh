<#include "security.ftlh">
<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-3">
            <label class="form-label"> User Name : </label>
            <div class="col-sm-6">
                <input class="form-control ${(usernameError??)?string('is-invalid','')}"
                       type="text" name="username" value="<#if user??>${user.username}</#if>"
                       placeholder="User name"/>
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label"> Password: </label>
            <div class="col-sm-6">
                <input class="form-control ${(passwordError??)?string('is-invalid','')}"
                       type="password" name="password"
                       placeholder="Password"/>
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="mb-3">
                <label class="form-label"> Password: </label>
                <div class="col-sm-6">
                    <input class="form-control ${(password2Error??)?string('is-invalid','')}"
                           type="password" name="password2"
                           placeholder="Retype password"/>
                    <#if password2Error??>
                        <div class="invalid-feedback">
                            ${password2Error}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label"> Email: </label>
                <div class="col-sm-6">
                    <input class="form-control ${(emailError??)?string('is-invalid','')}"
                           type="email" name="email" value="<#if user??>${user.email}</#if>"
                           placeholder="Email"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="g-recaptcha" data-sitekey="6LcY2D0gAAAAAOrtCgAdgjGH75P6T04sm5ronH6V"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Sign in</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary"><#if user??>Sign Out<#else>Log in</#if></button>
    </form>
</#macro>