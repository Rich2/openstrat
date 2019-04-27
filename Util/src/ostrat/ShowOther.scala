/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait ShowCompound[R] extends Show[R]
{
  final override def show(obj: R): String = typeStr + showSemi(obj).enParenth 
  @inline override def showTyped(obj: R): String = show(obj)
}

trait ShowSimple[A] extends Show[A]
{
  final override def syntaxDepth: Int = 1
  override def showComma(obj: A): String = show(obj)
  override def showSemi(obj: A): String = show(obj)
  override def showTyped(obj: A): String = typeStr - show(obj).enParenth
}

abstract class ShowSingleton[T](objSym: Symbol)
{
  def str: String = objSym.name
  override def toString: String = objSym.name
}
