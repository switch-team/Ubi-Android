package com.example.ubi.login.contact

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import kotlinx.coroutines.launch

class ContactViewModel:ViewModel() {
    val contact = MutableLiveData<List<Any>>()

    fun getContactList() = viewModelScope.launch {

    }
    private val loaderCallback = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            return CursorLoader(
                Context,
                Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber)),
                arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY),//projection : 원하는 column만 가져오기 위해 설정. null일 경우 모든 항목 가져옴
                null,//selection
                null,//selectionArgs
                null//sortOrder
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
            data?.apply {
                moveToFirst()
                while (!isAfterLast) {
                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME).takeIf { it >= 0 }?.let {
                        getString(it)
                    }?.let {
                        Log.d("contactDisplayName", it)
                    }
                    moveToNext()
                }
                close()
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
            TODO("Not yet implemented")
        }
    }
}