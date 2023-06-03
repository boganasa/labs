rootProject.name = "untitled"
include("lab3")
include("lab3:Dao")
findProject(":lab3:Dao")?.name = "Dao"
include("lab3:Service")
findProject(":lab3:Service")?.name = "Service"
include("lab3:Controller")
findProject(":lab3:Controller")?.name = "Controller"
include("lab5")
include("lab5:CatMicroservice")
findProject(":lab5:CatMicroservice")?.name = "CatMicroservice"
include("lab5:OwnerMicroservice")
findProject(":lab5:OwnerMicroservice")?.name = "OwnerMicroservice"
include("lab5:LibraryMicroservice")
findProject(":lab5:LibraryMicroservice")?.name = "LibraryMicroservice"
include("lab5:UI")
findProject(":lab5:UI")?.name = "UI"
