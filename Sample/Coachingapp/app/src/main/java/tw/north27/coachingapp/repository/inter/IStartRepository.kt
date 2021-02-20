package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.Data
import tw.north27.coachingapp.model.OriginateResult

interface IStartRepository {

    /**
     * 獲得起始數據
     * */
    suspend fun getOriginateData(): OriginateResult

    /***/
    suspend fun getData(): Data


}