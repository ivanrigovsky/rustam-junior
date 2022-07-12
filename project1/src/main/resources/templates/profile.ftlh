<#import "parts/common.ftlh" as c>

<@c.page>
<h5>${username}</h5>
${message?ifExists}
<form method="post">
    <div class="mb-3">
        <label class="form-label"> Password: </label>
        <div class="col-sm-6">
            <input class="form-control" type="password" name="password" placeholder="Password" />
        </div>
    </div>
        <div class="mb-3">
            <label class="form-label"> Email: </label>
            <div class="col-sm-6">
                <input class="form-control" type="email" name="email" placeholder="Email" value="${email!''}" />
            </div>
        </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit" class="btn btn-primary">Save</button>
</form>
</@c.page>