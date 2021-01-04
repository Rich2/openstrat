package ostrat

/** The stringer implicit class gives extension methods for Show methods from the implicit Show instance type A. */
class ShowerTypeExtensions[-A](ev: ShowT[A], thisVal: A)
{ /** Provides the standard string representation for the object. */
  @inline def str: String = ev.strT(thisVal)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  @inline def strComma: String = ev.showComma(thisVal)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  @inline def strSemi: String = ev.showSemi(thisVal)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  @inline def strTyped: String = ev.showT(thisVal, Show.Typed, -1)

  def str0: String = ev.showT(thisVal, Show.Standard, 0)
  def str1: String = ev.showT(thisVal, Show.Standard, 1)
  def str2: String = ev.showT(thisVal, Show.Standard, 2)
  def str3: String = ev.showT(thisVal, Show.Standard, 3)
  def showFields: String = ev.showT(thisVal, Show.StdFields, 1)
}

class Show2erTypeExtensions[A1, A2, -T](ev: Show2T[A1, A2, T], thisVal: T)
{
  @inline def strCommaNames: String = ev.showCommaNames(thisVal)
  @inline def strSemiNames: String = ev.showSemiNames(thisVal)
}