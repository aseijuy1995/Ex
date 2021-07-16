package tw.north27.coachingapp.consts.simulation

import tw.north27.coachingapp.model.response.EducationLevel
import tw.north27.coachingapp.model.response.Grade
import tw.north27.coachingapp.model.response.Subject
import tw.north27.coachingapp.model.response.Units

val educationList_Test = listOf<EducationLevel>(
    EducationLevel(id = 1, name = "國小"),
    EducationLevel(id = 2, name = "國中")
)

val gradeList_Test = listOf<Grade>(
    Grade(id = 1, name = "一年級", educationLevelId = 1),
    Grade(id = 2, name = "二年級", educationLevelId = 1),
    Grade(id = 3, name = "七年級", educationLevelId = 2)
)
val subjectList_Test = listOf<Subject>(
    Subject(id = 1, name = "國語", gradeIdList = listOf(1, 2)),
    Subject(id = 2, name = "數學", gradeIdList = listOf(1, 2, 3)),
    Subject(id = 3, name = "生活", gradeIdList = listOf(1)),
    Subject(id = 4, name = "國文", gradeIdList = listOf(3)),
    Subject(id = 5, name = "英文", gradeIdList = listOf(3)),
)
val unitList_Test = listOf<Units>(
    //國語
    Units(id = 1, name = "手拉手", educationLevelId = 1, gradeId = 1, subjectId = 1),
    Units(id = 2, name = "排一排", educationLevelId = 1, gradeId = 1, subjectId = 1),
    Units(id = 3, name = "踩影子", educationLevelId = 1, gradeId = 2, subjectId = 1),
    Units(id = 4, name = "再玩一次", educationLevelId = 1, gradeId = 2, subjectId = 1),
    //數學
    Units(id = 5, name = "加減", educationLevelId = 1, gradeId = 1, subjectId = 2),
    Units(id = 6, name = "乘除", educationLevelId = 1, gradeId = 1, subjectId = 2),
    Units(id = 7, name = "數與數線", educationLevelId = 1, gradeId = 2, subjectId = 2),
    Units(id = 8, name = "公因數&公倍數", educationLevelId = 1, gradeId = 2, subjectId = 2),
    Units(id = 9, name = "機率統計", educationLevelId = 2, gradeId = 3, subjectId = 2),
    Units(id = 10, name = "三角函數", educationLevelId = 2, gradeId = 3, subjectId = 2),
    //生活
    Units(id = 11, name = "上一年級了", educationLevelId = 1, gradeId = 1, subjectId = 3),
    Units(id = 12, name = "我的新學校", educationLevelId = 1, gradeId = 1, subjectId = 3),
    //國文
    Units(id = 13, name = "夏夜 楊喚", educationLevelId = 2, gradeId = 3, subjectId = 4),
    Units(id = 14, name = "論語選", educationLevelId = 2, gradeId = 3, subjectId = 4),
    //英文
    Units(id = 17, name = "Have You Decided on the Gift?", educationLevelId = 2, gradeId = 3, subjectId = 5),
    Units(id = 18, name = "Seeing Is Believing, Isn't It?", educationLevelId = 2, gradeId = 3, subjectId = 5),
)