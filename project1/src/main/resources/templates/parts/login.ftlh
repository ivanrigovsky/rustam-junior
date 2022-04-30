<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-3">
            <label class="form-label"> User Name : </label>
            <div class="col-sm-6">
                <input class="form-control" type="text" name="username" placeholder="User name"/>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label"> Password: </label>
            <div class="col-sm-6">
                <input class="form-control" type="password" name="password" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
        <div class="mb-3">
            <label class="form-label"> Email: </label>
            <div class="col-sm-6">
                <input class="form-control" type="email" name="email" placeholder="Email"/>
            </div>
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
        <button type="submit" class="btn btn-primary">Sign Out</button>
    </form>
</#macro>