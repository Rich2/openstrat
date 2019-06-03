/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import reflect.ClassTag, pParse._

abstract class ShowSum2[ST <: AnyRef, A1 <: ST , A2 <: ST](implicit val ct1: ClassTag[A1], val ct2: ClassTag[A2]) extends Show[ST]
{
  def ev1: Show[A1]
  def ev2: Show[A2]
  
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
  override def fromExpr(expr: Expr): EMon[ST] =
    pList.mapFirstGood(_.fromExpr(expr), bad1(expr.startPosn, "fromExpr, No value of" -- typeStr -- "found."))
    
  override def fromClauses(clauses: List[Clause]): EMon[ST] =
    pList.mapFirstGood(_.fromClauses(clauses), bad1(clauses(0).startPosn, "fromClauses No value of" -- typeStr -- "found."))
    
  override def fromStatements(sts: List[Statement]): EMon[ST] =
    pList.mapFirstGood(_.fromStatements(sts), bad1(sts.startPosn, "fromStatements, No value of" -- typeStr -- "found."))  
}

abstract class PersistSum2[ST <: AnyRef, A1 <: ST , A2 <: ST](val ev1: Persist[A1], val ev2: Persist[A2])(implicit ct1: ClassTag[A1],
    ct2: ClassTag[A2]) extends ShowSum2[ST, A1, A2] with UnShowSum2[ST, A1, A2] with Persist[ST]
{

}

