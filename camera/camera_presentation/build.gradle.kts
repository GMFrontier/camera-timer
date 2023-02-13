apply{
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.cameraDomain))

    "implementation"(CameraX.cameraCore)
    "implementation"(CameraX.camera2)
    "implementation"(CameraX.cameraLifecycle)
    "implementation"(CameraX.cameraView)
    "implementation"(CameraX.cameraExtensions)
    "implementation"(Permissions.permission)

    "implementation"(Coil.coilCompose)
    "implementation"(kotlin("reflect"))
}