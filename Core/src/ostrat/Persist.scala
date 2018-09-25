/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The essential persistence type class. Builds objects of the type from CRON syntax. */
abstract class Persist[T](val typeSym: Symbol)
{
   //def str: String// = persistFull.strFold("\n")  
   //def persistComma: Seq[String]
   //def persistFull: Seq[String]
   def syntaxDepth: Int
   
   def typeStr: String = typeSym.name   
   def persist(obj: T): String
   def persistComma(obj: T): String
   def persistSemi(obj: T): String
  // def persistTyped(obj: T): String   
   
   def fromExpr(expr: Expr): EMon[T]
   def fromClauses(clauses: Seq[Clause]): EMon[T]
   def fromClauses2[A1, A2, B](f: (A1, A2) => B, clauses: Seq[Clause])(implicit ev1: Persist[A1], ev2: Persist[A2]): EMon[B] =
      clauses match
   {
      case Seq(c1, c2) => ev1.fromExpr(c1.expr).map2(ev2.fromExpr(c2.expr), f)
      case _ => excep("from clauses exception")
   }
   
   def fromClauses3[A1, A2, A3, B](f: (A1, A2, A3) => B, clauses: Seq[Clause])
      (implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3]): EMon[B] = clauses match
   {
      case Seq(c1, c2, c3) => ev1.fromExpr(c1.expr).map3(ev2.fromExpr(c2.expr), ev3.fromExpr(c3.expr), f)
   }
   def fromClauses4[A1, A2, A3, A4, B](f: (A1, A2, A3, A4) => B, clauses: Seq[Clause])
      (implicit ev1: Persist[A1], ev2: Persist[A2], ev3: Persist[A3], ev4: Persist[A4]): EMon[B] = clauses match
   {
      case Seq(c1, c2, c3, c4) => ev1.fromExpr(c1.expr).map4(ev2.fromExpr(c2.expr), ev3.fromExpr(c3.expr), ev4.fromExpr(c4.expr), f)
   }
   /** Trys to build an object of type T from the statement */
   def fromStatement(st: Statement): EMon[T]
   /** Tries to find first statement that can build an object of type T and returns object */
   def fromStatementSeq(s: Seq[Statement]): EMon[T] = s match
   {
      case Seq() => FilePosn.emptyError("No Statements")
      case Seq(e1) => fromStatement(e1)
      case s2 => s.map(fromStatement(_)).collect{ case g: Good[T] => g } match
      {
         case Seq(t) => t
         case Seq() => bad1(s.startPosn, typeStr -- "not found.")
         case s3 => bad1(s.startPosn, s3.length.toString -- "values of" -- typeStr -- "found.")
      }
   }
}

object Persist
{
   implicit object IntPersist extends PersistSimple[Int]('Int)
   {      
      def persist(obj: Int): String = obj.toString
      override def fromExpr(expr: Expr): EMon[Int] = expr match      
      {
         case IntToken(_, _, i) => Good(i)
         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i)
         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i)
         
         case  _ => expr.exprParseErr[Int]
      }
   }
   
   implicit object StringPersist extends PersistSimple[String]('Str)
   {      
      def persist(obj: String): String = obj.enqu
      override def fromExpr(expr: Expr): EMon[String] = expr match      
      {
         case StringToken(_, stringStr) => Good(stringStr)        
         case  _ => expr.exprParseErr[String]
      }
   }
   
//   implicit object LongPersist extends PersistSimple[Long]
//   {
//      override def persistType = 'Long
//      override def isType(obj: Any): Boolean = obj.isInstanceOf[Long]     
//      override def fromExpr(expr: Expr): EMon[Long] = expr match      
//      {
//         case IntToken(_, _, i) => Good(i.toLong)
//         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toLong)
//         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i.toLong)
//         case LongIntToken(_, _, li) => Good(li)
//         case PreOpExpr(op, LongIntToken(_, _, li)) if op.str == "+" => Good(li)
//         case PreOpExpr(op, LongIntToken(_, _, li)) if op.str == "-" => Good(-li)
//         case  _ => expr.exprParseErr[Long]
//      }
//   }
   
   //implicit def seqToPerister[A](implicit ev: Persist[A]) = new SeqPersist[A](ev)
   
//   class SeqPersist[A](ev: Persist[A]) extends Persist[Seq[A]]//()//((sa: Seq[A]) => persisttraversableToRichImp[A](sa)(ev))
//   {
//      override def persistType = 'Seq// 
//      override def typeStr = persistType.name - ev.typeStr.enSquare
//      override def memStrs: Seq[A] => Seq[String] = _.map(ev.persistObj(_))
//      override def isType(obj: Any): Boolean = obj match
//      {
//         case s @ Seq(mems) => s.forall(el => ev.isType(el)) 
//         case _ => false   
//      }
//      override def fromExpr(expr: Expr): EMon[Seq[A]] = expr match
//      {
//         case SemicolonToken(_) => Good(Seq[A]())
//         //For Some reason the compile is not finding the implicit
//         case AlphaBracketExpr(AlphaToken(_, "Seq"), Seq(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) => sts.eMonMap[A](_.errGet[A](ev))
//         case e => bad1(expr, "Unknown Exoression for Seq")
//      }
//      override def fromClauses(clauses: Seq[Clause]): EMon[Seq[A]] = clauses.eMonMap (cl => ev.fromExpr(cl.expr))
//      override def fromStatement(st: Statement): EMon[Seq[A]] = st match
//      {
//         case MonoStatement(expr, _) => fromExpr(expr)
//         case ClausedStatement(clauses, _) => fromClauses(clauses)
//         case es @ EmptyStatement(_) => es.asError         
//      }
//   }
   
//   implicit object FloatPersist extends PersistSimple[Float]
//   {
//      override def persistType = 'Flt32
//      override def isType(obj: Any): Boolean = obj.isInstanceOf[Float]
//      override def fromExpr(expr: Expr): EMon[Float] = expr match      
//      {
//         case IntToken(_, _, i) => Good(i.toFloat)
//         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toFloat)
//         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-(i.toFloat))
//         case FloatToken(_, _, d) => Good(d.toFloat)
//         case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "+" => Good(d.toFloat)
//         case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "-" => Good(-d.toFloat)
//         case  _ => expr.exprParseErr[Float]
//      }
//   }
   implicit object DoublePersist extends PersistSimple[Double]('Flt)
   {      
      def persist(obj: Double): String = obj.toString      
      override def fromExpr(expr: Expr): EMon[Double] = expr match      
      {
         case IntToken(_, _, i) => Good(i.toDouble)
         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toDouble)
         case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-(i.toDouble))
         case FloatToken(_, _, d) => Good(d)
         case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "+" => Good(d)
         case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "-" => Good(-d)
         case  _ => expr.exprParseErr[Double]
      } 
   }   
   
   implicit object BooleanPersist extends PersistSimple[Boolean]('Bool)
   {
      //override def persistType = 'Bool
      override def persist(obj: Boolean): String = obj.toString  
      override def fromExpr(expr: Expr): EMon[Boolean] = expr match
      {
         case AlphaToken(_, str) if str == 'true => Good(true)
         case AlphaToken(_, str) if str == 'false => Good(false)
         case _ => expr.exprParseErr[Boolean]
      }
   }   
}