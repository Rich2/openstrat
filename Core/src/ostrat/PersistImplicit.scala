package ostrat

class PersistImplicit[A](ev: Persist[A], thisVal: A)
{ /** Provides the standard string representation for the object */
  def persist: String = ev.persist(thisVal)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
    *  happen if the syntax depth is less than 2. if it is 2 or greater return the full typed data. */
  def persistComma: String = ev.persistComma(thisVal)

  /** Return the defining member values of the type as a series of semicolon seperated values without enclosing type information, note this will only
    *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. This method is not commonly needed but is useful
    *  for case classes with a single member. */
  def persistSemi: String = ev.persistSemi(thisVal)
}
