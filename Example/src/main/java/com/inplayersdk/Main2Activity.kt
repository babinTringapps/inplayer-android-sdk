package com.inplayersdk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.sdk.inplayer.configuration.InPlayer
import com.sdk.inplayer.callback.InPlayerCallback
import com.sdk.inplayer.callback.InPlayerNotificationCallback
import com.sdk.inplayer.model.error.InPlayerException
import com.sdk.inplayer.model.notification.InPlayerNotification
import com.sdk.inplayer.model.notification.InPlayerNotificationStatus
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.content_main2.*

class Main2Activity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)
        
        
        login.setOnClickListener {
            logInuser()
        }
        
        logout.setOnClickListener {
            logOut()
        }
        
        sign_up.setOnClickListener {
            signUp()
        }
        
        account_details.setOnClickListener {
            accountDetails()
        }
        
        erase.setOnClickListener {
            eraseUser()
        }
        
        changePassword.setOnClickListener {
            changePassword()
        }
        
        forgot_password.setOnClickListener {
            forgotPassword("victorpetrovski93+test94@gmail.com")
        }
        
        update.setOnClickListener {
            updateUser("HIKTORRRR")
        }
        
        set_new_password.setOnClickListener {
            val password = "newpassword12345678"
            setNewPassword("94c2cd8235e3c3a9", password, password)
        }
        
        get_access.setOnClickListener {
            getAccess()
        }
        
        get_access_fee.setOnClickListener {
            getAccessFees()
        }
        
        get_item.setOnClickListener {
            getItem()
        }
        
        notification.setOnClickListener {
            initNotification()
        }
        
        publish.setOnClickListener {
            val account = InPlayer.Account.getAccount()
            
        }
        
    }
    
    private fun logInuser() {
        InPlayer.Account.authenticate("YOUR_USERNAME_HERE", "YOUR_PASSWORD_HERE", InPlayerCallback { inPlayerUser, error ->
            if (error == null) {
                //SUCCESS - Handle InPlayerUser
            } else {
                //Handle Error
            }
        })
    }
    
    private fun logOut() {
        InPlayer.Account.logout(InPlayerCallback { sucessMessage, error -> })
    }
    
    private fun signUp() {
        InPlayer.Account.createAccount("Viktor Petrovski",
                "victorpetrovski93+test9954@gmail.com",
                "androidsdk123",
                "androidsdk123", InPlayerCallback { inPlayerUser, error ->
            if (error == null) {
                Log.v("signUp", "User created $inPlayerUser")
            } else {
                //error.printStackTrace()
            }
        })
    }
    
    private fun accountDetails() {
        InPlayer.Account.getAccountDetails(InPlayerCallback { inPlayerUser, error ->
            if (error == null) {
                //Handle InPlayerUser
                Log.v("accountDetails", "User Details: $inPlayerUser")
            } else {
                error.errorsList
                error.e.printStackTrace()
            }
        })
    }
    
    private fun eraseUser() {
        InPlayer.Account.eraseAccount("androidsdk123", InPlayerCallback { sucessMessage, error -> })
    }
    
    private fun changePassword() {
        InPlayer.Account.changePassword("newpassword12345", "newpassword12345", "newpassword123",
                InPlayerCallback { sucessMessage, error ->
    
                })
    }
    
    private fun forgotPassword(email: String) {
        InPlayer.Account.forgotPassword(email, InPlayerCallback { sucessMessage, error ->
            if (error == null) {
                //Handle InPlayerUser
                Log.v("forgotPassword", sucessMessage)
            } else {
                //Handle Error
                //  error.printStackTrace()
            }
        })
    }
    
    private fun updateUser(fullName: String) {
        val map = HashMap<String, String>()
        map["country"] = "Spain"
        
        InPlayer.Account.updateAccount(fullName, map, InPlayerCallback { inPlayerUser, error ->
            if (error == null) {
                Log.v("updateAccount", "$inPlayerUser")
            } else {
                //Handle Error
                error.e.printStackTrace()
            }
        })
    }
    
    private fun setNewPassword(token: String, newPassword: String, newPasswordConfirmation: String) {
        InPlayer.Account.setNewPassword(token, newPassword, newPasswordConfirmation, InPlayerCallback { message, error ->
            if (error == null) {
                Log.v("setNewPassword", "User setNewPassword: $message")
            } else {
                //Handle Error
                Log.v("setNewPassword", "Error block $message")
            }
        })
    }
    
    private fun getAccess() {
        InPlayer.Assets.getItemAccess(43871, InPlayerCallback { itemAccess, error ->
            if (error == null) {
                Log.v("getAccess", "Access: $itemAccess")
            } else {
                //Handle Error
                Log.v("getAccess", "Error block $error")
                error.e.printStackTrace()
            }
        })
    }
    
    private fun getItem() {
        InPlayer.Assets.getItemDetails(1111, InPlayerCallback { inPlayerItem, error ->
            if (error == null) {
                //SUCCESS - Handle InPlayerItem
            } else {
                //Handle Error
                Log.v("getItem", "Error block $error")
                error.e.printStackTrace()
            }
        })
    }
    
    private fun getAccessFees() {
        InPlayer.Assets.getAccessFees(43871, InPlayerCallback { accsFee, error ->
            if (error == null) {
                Log.v("getAccessFees", "Access Fees: $accsFee")
            } else {
                //Handle Error
                Log.v("getAccessFees", "Error block $error")
                error.e.printStackTrace()
            }
        })
    }
    
    private fun initNotification() {
   
    }
    
    override fun onResume() {
        super.onResume()
    
        InPlayer.Notification.subscribe(object : InPlayerNotificationCallback {
        
            override fun onStatusChanged(status: InPlayerNotificationStatus) {  }
        
            override fun onMessageReceived(message: InPlayerNotification) { }
        
            override fun onError(t: InPlayerException) {    }
        })
    }
    
}
