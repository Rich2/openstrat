/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import reflect.ClassTag, pParse._

trait ShowSum2[ST <: AnyRef, A1 <: ST , A2 <: ST] extends Show[ST]
{
  def ev1: Show[A1]  
  def ev2: Show[A2]
  implicit def ct1: ClassTag[A1]
  implicit def ct2: ClassTag[A2]

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

trait UnShowSum2[+ST <: AnyRef, A1 <: ST , A2 <: ST] extends UnShow[ST]
{
  def ev1: UnShow[A1]  
  def ev2: UnShow[A2] 
  
  def pList: List[UnShow[ST]] = List(ev1, ev2)
  override def fromExpr(expr: Expr): EMon[ST] = pList.mapFirstGood(_.fromExpr(expr), bad1(expr.startPosn, "No value of" -- typeStr -- "found."))
    
  override def fromClauses(clauses: Seq[Clause]): EMon[ST] = ???  
  def fromStatement(st: Statement): EMon[ST] = ???
}

class PersistSum2[ST <: AnyRef, A1 <: ST , A2 <: ST](val typeStr: String)(implicit val ev1: Persist[A1], val ct1: ClassTag[A1], val ev2: Persist[A2],
    val ct2: ClassTag[A2]) extends ShowSum2[ST, A1, A2] with UnShowSum2[ST, A1, A2]
{

}

