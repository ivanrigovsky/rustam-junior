<#import "parts/common.ftlh" as c>

<@c.page>
<form class="row g-3 my-3">
    <div class="col-auto">
        <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by tag" />
    </div>
    <div class="col-auto">
        <button type="submit" class="btn btn-primary ml-2">Search</button>
    </div>
</form>

<#include "parts/messageEdit.ftlh" />
<#include "parts/messageList.ftlh" />

</@c.page>