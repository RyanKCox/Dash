package com.revature.dash.presentation.controllers.title

import android.content.Context
import android.widget.Toast
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.revature.dash.presentation.controllers.mainmenu.MainMenuController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class TitlePresenter :MviBasePresenter<TitleView,TitleVS>(){
    override fun bindIntents() {


        val fakeLoading = Observable.just(TitleVS.Loading)
            .ofType(TitleVS::class.java)

        val enterClick = intent { it.enterIntent() }
            .doOnNext { router->
                //Navigate
                router.pushController(RouterTransaction.with(MainMenuController()))

            }
            .ofType(TitleVS::class.java)

        val data = Observable.just(TitleVS.Display)
            .delay(2,TimeUnit.SECONDS)
            .ofType(TitleVS::class.java)


        val viewState = fakeLoading
            .mergeWith(data)
            .mergeWith(enterClick)
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(viewState){view,state-> view.render(state)}
    }

}

interface TitleView:MvpView{

    fun enterIntent():Observable<Router>
    fun render(state:TitleVS)
}
sealed class TitleVS{
    object Loading:TitleVS()
    object Display:TitleVS()
}