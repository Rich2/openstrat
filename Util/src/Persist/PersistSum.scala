/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag, pParse._

/** Show class for algebraic sum types. If you are using your own code then Show sum types handled by inheritance. */
abstract class ShowSum2[ST <: AnyRef, A1 <: ST, A2 <: ST]()(implicit val ct1: ClassTag[A1], val ct2: ClassTag[A2]) extends ShowT[ST]
{
  def ev1: ShowT[A1]
  def ev2: ShowT[A2]

  override def strT(obj: ST): String = obj match
  { case a1: A1 => ev1.strT(a1)
    case a2: A2 => ev2.strT(a2)
    case _ => excep("Case not implemented")
  }
  
  override def syntaxDepthT(obj: ST): Int = 3//ev1.syntaxDepth(obj.).max(ev2.syntaxDepth())
  
  /*override def showComma(obj: ST): String = obj match
  { case a1: A1 => ev1.showComma(a1)
    case a2: A2 => ev2.showComma(a2)
  }
  
  override def showSemi(obj: ST): String = obj match
  { case a1: A1 => ev1.showSemi(a1)
    case a2: A2 => ev2.showSemi(a2)
  }*/

  override def showT(obj: ST, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
}

object ShowSum2
{
  def apply[ST <: AnyRef, A1 <: ST, A2 <: ST](typeIn: String, ev1In: ShowT[A1], ev2In: ShowT[A2])(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]):
    ShowSum2[ST, A1, A2] = new ShowSum2[ST, A1, A2]()(ct1, ct2)
  { override def typeStr: String = typeIn
    override def ev1: ShowT[A1] = ev1In
    override def ev2: ShowT[A2] = ev2In
  }
}

trait UnShowSum2[+ST <: AnyRef, A1 <: ST , A2 <: ST] extends UnShow[ST]
{
  def ev1: UnShow[A1]  
  def ev2: UnShow[A2] 
  
  def pList: List[UnShow[ST]] = List(ev1, ev2)
  override def fromExpr(expr: Expr): EMon[ST] =
    pList.mapFirstGood(_.fromExpr(expr), expr.startPosn.bad("fromExpr, No value of" -- typeStr -- "found."))
    
 /* override def fromClauses(clauses: Refs[Clause]): EMon[ST] =
    pList.mapFirstGood(_.fromClauses(clauses), clauses(0).startPosn.bad("fromClauses No value of" -- typeStr -- "found."))
    
  override def fromStatements(sts: Refs[Statement]): EMon[ST] =
    pList.mapFirstGood(_.fromStatements(sts), sts.startPosn.bad("fromStatements, No value of" -- typeStr -- "found.")) */
}

abstract class PersistSum2[ST <: AnyRef, A1 <: ST , A2 <: ST](val ev1: Persist[A1], val ev2: Persist[A2])(implicit ct1: ClassTag[A1],
    ct2: ClassTag[A2]) extends ShowSum2[ST, A1, A2] with UnShowSum2[ST, A1, A2] with Persist[ST]