/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The classic Show type class. A functional version of toString .Mostly you will want to use Persist which not only gives the Show methods
 *   to String representation, but the methods to parse Strings back to objects of the type T. However it may often be useful to start with Show
 *   type class and upgrade it later to Persist[T]. */
abstract class Show[T]
{
  def typeStr: String
  /** Provides the standard string representation for the object */
  def show(obj: T): String
  def syntaxDepth: Int  
  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  def showComma(obj: T): String
  
  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  def showSemi(obj: T): String
  /** For most objects showTyped will return the same value as show(obj: T), for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4) */
  def showTyped(obj: T): String
}

trait ShowCompound[R] extends Show[R]
{
  final override def show(obj: R): String = typeStr + showSemi(obj).enParenth 
  @inline override def showTyped(obj: R): String = show(obj)
}

abstract class ShowSimple[A](val typeSym: Symbol) extends Show[A]
{
   final override def syntaxDepth: Int = 1
}

abstract class ShowSingleton[T](objSym: Symbol)
{
  def str: String = objSym.name
  override def toString: String = objSym.name
}
