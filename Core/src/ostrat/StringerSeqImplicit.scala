/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

class StringerSeqDirect[A](thisSeq: Seq[A], ev: Persist[A])
{ import ostrat.Persist.{PersistSeqImplicit => PSI }
  /** Provides the standard string representation for the object */
  def str: String = new PSI(ev).persist(thisSeq)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
    *  happen if the syntax depth is less than 2. if it is 2 or greater return the full typed data. */
  def strComma: String = new PSI(ev).persistComma(thisSeq)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
    *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. This method is not commonly needed but is useful
    *  for case classes with a single member. */
  def strSemi: String = new PSI(ev).persistSemi(thisSeq)

  /** For most objects persistTyped wil return the same value as persist, for PeristValues the value will be type enclosed. 4.persistTyped
    * will return Int(4) */
  def strTyped: String = new PSI(ev).persistTyped(thisSeq)
}
