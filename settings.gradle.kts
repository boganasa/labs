rootProject.name = "untitled"
include("lab3")
include("lab3:Dao")
findProject(":lab3:Dao")?.name = "Dao"
include("lab3:Service")
findProject(":lab3:Service")?.name = "Service"
include("lab3:Controller")
findProject(":lab3:Controller")?.name = "Controller"
