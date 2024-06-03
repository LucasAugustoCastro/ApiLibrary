package com.lucasaugustocastro.ApiLibrary.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("security")
data class SecurityProperties @ConstructorBinding constructor(
    val issuer: String,
    val secret: String,
    val expireHours: Long,
    val adminExpireHours: Long
)
