/*
 * Copyright (c) KienHT - 2019.
 */

package com.vcc.trackcar.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

/**
 * @author kienht
 * @company OICSoft
 * @since 11/06/2019
 */

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showToast(@StringRes message: Int) = showToast(getString(message))

fun Context.showToastWarning(message: String) = Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show()

fun Context.showToastWarning(@StringRes message: Int) = showToastWarning(getString(message))

fun Context.showToastError(message: String) = Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()

fun Context.showToastError(@StringRes message: Int) = showToastError(getString(message))

fun Context.showToastInfo(message: String) = Toasty.info(this, message, Toast.LENGTH_SHORT, true).show()

fun Context.showToastInfo(@StringRes message: Int) = showToastInfo(getString(message))

fun Context.showToastSuccess(message: String) = Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()

fun Context.showToastSuccess(@StringRes message: Int) = showToastSuccess(getString(message))

fun Fragment.showToast(message: String) = context?.showToast(message)

fun Fragment.showToast(@StringRes message: Int) = showToast(getString(message))

fun Fragment.showToastWarning(message: String) = context?.showToastWarning(message)

fun Fragment.showToastWarning(@StringRes message: Int) = showToastWarning(getString(message))

fun Fragment.showToastError(message: String) = context?.showToastError(message)

fun Fragment.showToastError(@StringRes message: Int) = showToastError(getString(message))

fun Fragment.showToastInfo(message: String) = context?.showToastInfo(message)

fun Fragment.showToastInfo(@StringRes message: Int) = showToastInfo(getString(message))

fun Fragment.showToastSuccess(message: String) = context?.showToastSuccess(message)

fun Fragment.showToastSuccess(@StringRes message: Int) = showToastSuccess(getString(message))