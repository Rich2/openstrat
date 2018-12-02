/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait IsType[A <: AnyRef]
{ def isType(obj: AnyRef): Boolean
  def asType(obj: AnyRef): A
  def optType(obj: AnyRef): Option[A] = ifSome(isType(obj), asType(obj))
}

object IsType
{
  implicit object AnyRefIsType extends IsType[AnyRef]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[AnyRef]
    override def asType(obj: AnyRef): AnyRef = obj.asInstanceOf[AnyRef]
  }
}

trait SymbolKey
{
  def sym: Symbol
}

object SymbolKey
{
  implicit class SymbolKeySeqImp[A <: SymbolKey](thisSeq: Seq[A])
  {
    def symFind(sym: Symbol): Option[A] = thisSeq.find(_.sym == sym)
    def symGet(sym: Symbol): A = symFind(sym).get
  }
}