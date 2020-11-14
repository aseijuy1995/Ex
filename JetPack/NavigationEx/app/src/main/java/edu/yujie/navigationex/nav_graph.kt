package edu.yujie.navigationex

/**
 * @author YuJie on 2020/11/14
 * @describe 說明
 * @param 參數
 */
object nav_graph {

    const val id = 1

    object kotlin_dsl {
        const val id = 2

        object action {
            const val frag_kotlin_dsl_to_frag_kotlin_dsl2 = 3
        }
    }

    object kotlin_dsl2 {
        const val id = 4

        object action {
            const val frag_kotlin_dsl2_to_frag_kotlin_dsl = 5
            const val frag_kotlin_dsl2_to_act_main = 7
        }
    }

    object main{
        const val id=6
    }

}