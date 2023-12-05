/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Extension methods for types with [[Show]] type class instances. */
class ShowingExtensions[-A](ev: Show[A], thisVal: A)
{ /** Provides the standard string representation for the object. */
  @inline def str: String = ev.strT(thisVal)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def show(style: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = 0): String = ev.show(thisVal, style, decimalPlaces, minPlaces)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  @inline def strComma: String = ev.show(thisVal, ShowCommas, -1, 0)

  def str2Comma: String = ev.show(thisVal, ShowCommas, 2, 0)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  @inline def strSemi: String = ev.show(thisVal, ShowSemis, -1, 0)

  @inline def strSemi(maxPlaces: Int, minPlaces: Int = 0): String =  ev.show(thisVal, ShowSemis, maxPlaces, minPlaces)

  def strMin: String = ev.show(thisVal, ShowMinimum)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4). */
  @inline def strTyped: String = ev.show(thisVal, ShowTyped, -1, 0)

  def str0: String = ev.show(thisVal, ShowStandard, 0, 0)
  def str1: String = ev.show(thisVal, ShowStandard, 1, 0)
  def str2: String = ev.show(thisVal, ShowStandard, 2, 0)
  def str3: String = ev.show(thisVal, ShowStandard, 3, 0)

  /** Shows this object with field names. */
  def showFields: String = ev.show(thisVal, ShowFieldNames, -1, 0)

  /** Shows this object with field names and field types. */
  def showTypedFields: String = ev.show(thisVal, ShowStdTypedFields, -1, 0)

  /** the syntactic depth of the [[Show]] for this object. */
  def showDepth: Int = ev.syntaxDepth(thisVal)
}