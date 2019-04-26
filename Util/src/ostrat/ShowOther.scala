/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

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
