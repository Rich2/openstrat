/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.immutable.ArraySeq

/** A type class for string, text and visual representation of objects. An alternative to toString. This trait has mor demanding ambitions Mostly you
 *  will want to use  Persist which not only gives the Show methods to String representation, but the methods to parse Strings back to objects of the
 *  type T. However it may often be useful to start with Show type class and upgrade it later to Persist[T]. The capabilities of decimal place
 *  precision and explicit typing for numbers are placed defined here and in the corresponding [[SHow]] type class although they have n meaning /
 *  purpose for many types, as separating them adds enormous complexity for very little gain. */
trait ShowDecT[-T] extends ShowT[T]
{
  def showDecT(obj: T, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String

  def showT(obj: T, style: ShowStyle): String = showDecT(obj, style, -1, -1)
}

/** The stringer implicit class gives extension methods for Show methods from the implicit Show instance type A. */
class ShowDecTExtensions[-A](ev: ShowDecT[A], thisVal: A)
{ /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  def show(style: ShowStyle = ShowStandard, decimalPlaces: Int = -1): String = ev.showDecT(thisVal, style, decimalPlaces, decimalPlaces)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  def show(style: ShowStyle, decimalPlaces: Int, minPlaces: Int): String = ev.showDecT(thisVal, style, decimalPlaces, minPlaces)


  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  @inline def strComma: String = ev.showDecT(thisVal, ShowCommas, -1, 0)//ev.showComma(thisVal)

  def str2Comma: String = ev.showDecT(thisVal, ShowCommas, 2, 0)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  @inline def strSemi: String = ev.showDecT(thisVal, ShowSemis, -1, 0)

  @inline def strSemi(maxPlaces: Int, minPlaces: Int = 0): String =  ev.showDecT(thisVal, ShowSemis, maxPlaces, minPlaces)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  @inline def strTyped: String = ev.showDecT(thisVal, ShowTyped, -1, 0)

  def str0: String = ev.showDecT(thisVal, ShowStandard, 0, 0)
  def str1: String = ev.showDecT(thisVal, ShowStandard, 1, 0)
  def str2: String = ev.showDecT(thisVal, ShowStandard, 2, 0)
  def str3: String = ev.showDecT(thisVal, ShowStandard, 3, 0)
  def showFields: String = ev.showDecT(thisVal, ShowParamNames, 1, 0)
  def showTypedFields: String = ev.showDecT(thisVal, ShowStdTypedFields, 1, 0)
}