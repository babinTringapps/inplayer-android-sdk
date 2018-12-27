package com.s.domain.usecase.autehntication

import com.s.domain.gateway.InPlayerAccountRepository
import com.s.domain.schedulers.MySchedulers
import com.s.domain.usecase.base.SingleUseCase
import io.reactivex.Single

/**
 * Created by victor on 12/26/18
 */
class EraseUserUseCase constructor(val mySchedulers: MySchedulers,
                                   val inPlayerAuthenticatorRepository: InPlayerAccountRepository)
    : SingleUseCase<String, EraseUserUseCase.Params>(mySchedulers) {
    
    override fun buildUseCaseObservable(params: Params?): Single<String> {
        
        params?.let {
            return inPlayerAuthenticatorRepository.eraseUser(it.password)
        }
        
        throw IllegalStateException("Params can't be null for EraseUserUseCase")
    }
    
    
    data class Params(val password: String)
    
}