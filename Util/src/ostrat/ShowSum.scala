/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import reflect.ClassTag, pParse._

class ShowSum2[ST <: AnyRef, A1 <: ST , A2 <: ST](val typeStr: String)(
    implicit ev1: Show[A1], ct1: ClassTag[A1], ev2: Show[A2], ct2: ClassTag[A2]) extends Show[ST]
{
  override def show(obj: ST): String = obj match
  {
    case a1: A1 => ev1.show(a1)
    case a2: A2 => ev2.show(a2)
  }
  
  override def syntaxDepth: Int = ev1.syntaxDepth.max(ev2.syntaxDepth)
  
  override def showComma(obj: ST): String = obj match
  {
    case a1: A1 => ev1.showComma(a1)
    case a2: A2 => ev2.showComma(a2)
  }
  
  override def showSemi(obj: ST): String = obj match
  {
    case a1: A1 => ev1.showSemi(a1)
    case a2: A2 => ev2.showSemi(a2)
  }
  
  override def showTyped(obj: ST): String = obj match
  {
    case a1: A1 => ev1.showTyped(a1)
    case a2: A2 => ev2.showTyped(a2)
  }  
}

class PersistSum2[ST <: AnyRef, A1 <: ST , A2 <: ST](typeStr: String)(implicit ev1: Persist[A1], ct1: ClassTag[A1], ev2: Persist[A2],
    ct2: ClassTag[A2]) extends ShowSum2[ST, A1, A2](typeStr) with Persist[ST]
{
  def pList: List[Persist[ST]] = List(ev1, ev2).asInstanceOf[List[Persist[ST]]]
  override def fromExpr(expr: Expr): EMon[ST] = pList.mapFirstGood(_.fromExpr(expr), bad1(expr.startPosn, "No value of" -- typeStr -- "found"))
    
  override def fromClauses(clauses: Seq[Clause]): EMon[ST] = ???  
  def fromStatement(st: Statement): EMon[ST] = ???
}

trait MyA[+T]
trait MyB[-T]
trait MyC[T] extends MyA[T] with MyB[T]
  