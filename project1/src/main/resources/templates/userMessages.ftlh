<#import "parts/common.ftlh" as c>

<@c.page>
<h3>${userChannel.username}</h3>
<#if !isCurrentUser>
    <#if isSubcriber>
        <a class="btn btn-info" href="/user/unsubscribe/${userChannel.username}">Unsubscribe</a>
    <#else>
        <a class="btn btn-info" href="/user/subscribe/${userChannel.username}">Subscribe</a>
    </#if>
</#if>
<div class="container my-3">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Subscription</h5>
                    <h3 class="card-text"></h3>
                    <a href="/user/subscriptions/${userChannel.username}/list">${subscriptionsCount}</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Subscribers</h5>
                    <h3 class="card-text"></h3>
                    <a href="/user/subscribers/${userChannel.username}/list">${subscribersCount}</a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card"></div>
        </div>
    </div>
</div>
<#if isCurrentUser>
    <#include "parts/messageEdit.ftlh" />
</#if>

<#include "parts/messageList.ftlh" />

</@c.page>