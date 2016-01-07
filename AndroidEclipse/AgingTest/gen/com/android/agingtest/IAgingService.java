/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\SourceCode\\Android-Eclipse\\AgingTest\\src\\com\\android\\agingtest\\IAgingService.aidl
 */
package com.android.agingtest;
public interface IAgingService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.android.agingtest.IAgingService
{
private static final java.lang.String DESCRIPTOR = "com.android.agingtest.IAgingService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.android.agingtest.IAgingService interface,
 * generating a proxy if needed.
 */
public static com.android.agingtest.IAgingService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.android.agingtest.IAgingService))) {
return ((com.android.agingtest.IAgingService)iin);
}
return new com.android.agingtest.IAgingService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_isVibrationTestOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isVibrationTestOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isSpeakerTestOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSpeakerTestOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isEarpieceTestOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isEarpieceTestOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setVibrationEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setVibrationEnabled(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSpeakerTestEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setSpeakerTestEnabled(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setEarpieceTestEnabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setEarpieceTestEnabled(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addStateChangedCallback:
{
data.enforceInterface(DESCRIPTOR);
com.android.agingtest.OnStateChangedCallback _arg0;
_arg0 = com.android.agingtest.OnStateChangedCallback.Stub.asInterface(data.readStrongBinder());
this.addStateChangedCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeStateChangedCallback:
{
data.enforceInterface(DESCRIPTOR);
com.android.agingtest.OnStateChangedCallback _arg0;
_arg0 = com.android.agingtest.OnStateChangedCallback.Stub.asInterface(data.readStrongBinder());
this.removeStateChangedCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.android.agingtest.IAgingService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public boolean isVibrationTestOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isVibrationTestOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isSpeakerTestOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSpeakerTestOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isEarpieceTestOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isEarpieceTestOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setVibrationEnabled(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setVibrationEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setSpeakerTestEnabled(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setSpeakerTestEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setEarpieceTestEnabled(boolean enabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((enabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setEarpieceTestEnabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void addStateChangedCallback(com.android.agingtest.OnStateChangedCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addStateChangedCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeStateChangedCallback(com.android.agingtest.OnStateChangedCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeStateChangedCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_isVibrationTestOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_isSpeakerTestOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_isEarpieceTestOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setVibrationEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setSpeakerTestEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setEarpieceTestEnabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_addStateChangedCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_removeStateChangedCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
}
public boolean isVibrationTestOn() throws android.os.RemoteException;
public boolean isSpeakerTestOn() throws android.os.RemoteException;
public boolean isEarpieceTestOn() throws android.os.RemoteException;
public void setVibrationEnabled(boolean enabled) throws android.os.RemoteException;
public void setSpeakerTestEnabled(boolean enabled) throws android.os.RemoteException;
public void setEarpieceTestEnabled(boolean enabled) throws android.os.RemoteException;
public void addStateChangedCallback(com.android.agingtest.OnStateChangedCallback callback) throws android.os.RemoteException;
public void removeStateChangedCallback(com.android.agingtest.OnStateChangedCallback callback) throws android.os.RemoteException;
}
