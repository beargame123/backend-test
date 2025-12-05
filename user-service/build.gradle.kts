plugins {
    id("kotlin-common-convention")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    testImplementation("org.springframework.graphql:spring-graphql-test")
}
