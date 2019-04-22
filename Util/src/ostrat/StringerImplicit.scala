package ostrat

/** The stringer implicit class gives extension methods for persist methods from the implicit Persist object for the type */
class StringerImplicit[A](ev: Persist[A], thisVal: A)
{ /** Provides the standard string representation for the object */
  def str: String = ev.show(thisVal)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
    *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  def strComma: String = ev.persistComma(thisVal)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
    *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
    *  for case classes with a single member. */
  def strSemi: String = ev.persistSemi(thisVal)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
    * will return Int(4) */
  def strTyped: String = ev.showTyped(thisVal)
}


