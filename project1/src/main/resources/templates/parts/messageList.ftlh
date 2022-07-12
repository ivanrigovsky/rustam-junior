<#include "security.ftlh">
<#import "pager.ftlh" as p>
<@p.pager url page />
<div class="my-3">
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <#list page.content as message>
            <div class="card m-3" style="width: 18rem;">
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}"  class="card-img-top" />
                    </#if>
                </div>
                <div class="m-2">
                    <span>${message.text}</span><br/>
                    <i>#${message.tag}</i>
                </div>
                <div class="card-footer text-muted container">
                    <div class="row">
                        <a class="col align-self-center" href="/user-messages/${message.author.username}">${message.authorName}</a>

                        <#if message.author.username == currentUserId>
                            <a class="col btn btn-primary" href="/user-messages/${message.author.username}?message=${message.id}">
                                Edit
                            </a>
                        </#if>
                    </div>
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</div>
<@p.pager url page/>