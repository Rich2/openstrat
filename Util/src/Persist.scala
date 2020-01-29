/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The essential persistence type class. it implements both a Show style type class interface, the production of a String representation of the value
  * but also produces an EMon[T] from a String. It Persists and builds objects of type T from CRON syntax. So for example the IntImplicit object in the
  * Persist companion object persists Integers and constructs Integers from Strings. */
trait Persist[T] extends Show[T] with UnShow[T]

/** Companion object for the persistence type class. Contains the implicit instances for Scala standard library types. */
object Persist
{  
  /** Implicit method for creating List[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def listToPersist[A](implicit ev: Persist[A]): Persist[List[A]] = new PersistListImplicit[A](ev)
  /** Implicit method for creating ::[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def consToPersist[A](implicit ev: Persist[A]): Persist[::[A]] = new PersistConsImplicit[A](ev)
  
  implicit def nilToPersist[A](implicit ev: Persist[A]): Persist[Nil.type] = new PersistNilImplicit[A](ev)
  
  /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqToPersist[T](implicit ev: Persist[T]): Persist[Seq[T]] = new PersistSeqImplicit[T](ev)
  /** Implicit method for creating Vector[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def vectorToPersist[T](implicit ev: Persist[T]): Persist[Vector[T]] = new PersistVectorImplicit[T](ev)  
  

  implicit def someToPersist[A](implicit ev: Persist[A]): Persist[Some[A]] = new Persist[Some[A]]
  {
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepth: Int = ev.syntaxDepth
    override def show(obj: Some[A]) = ev.show(obj.value)
    override def showSemi(obj: Some[A]) = ev.showSemi(obj.value)
    override def showComma(obj: Some[A]) = ev.showComma(obj.value)
    override def showTyped(obj: Some[A]) =ev.showTyped(obj.value)
    override def fromClauses(clauses: Refs[Clause]): EMon[Some[A]] = ev.fromClauses(clauses).map(Some(_))
    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    {
      case AlphaBracketExpr(IdentifierUpperToken(_, "Some"), Refs1(ParenthBlock(Refs1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
    override def fromStatements(sts: Refs[Statement]): EMon[Some[A]] = ev.fromStatements(sts).map(Some(_))
  }

  implicit val noneImplicit: Persist[None.type] = new PersistSimple[None.type]("None")
  {
    override def show(obj: None.type) = ""
    def fromExpr(expr: Expr): EMon[None.type] = expr match
    {
      case IdentifierLowerOnlyToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }

    override def fromStatements(sts: Refs[Statement]): EMon[None.type] = ife(sts.empty, Good(None), sts.startPosn.bad("None not found."))
  }

  implicit def optionToPersist[A](implicit evA: Persist[A]): Persist[Option[A]] =
    new PersistSum2[Option[A], Some[A], None.type](someToPersist[A](evA), noneImplicit)
    { override def typeStr: String = "Option" + evA.typeStr.enSquare
      override def syntaxDepth: Int = evA.syntaxDepth
    }
  
  implicit val charImplicit: Persist[Char] = new PersistSimple[Char]("Char")
  { def show(obj: Char): String = obj.toString.enquote1
    override def fromExpr(expr: Expr): EMon[Char] = expr match      
    { case CharToken(_, char) => Good(char)        
      case  _ => expr.exprParseErr[Char]
    }
  }
   

   
  implicit val floatPersist: Persist[Float] = new PersistSimple[Float]("SFloat")
  { def show(obj: Float): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Float] = expr match      
    { case DecimalToken(_, i) => Good(i.toFloat)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "+" => Good(i.toFloat)
      case PreOpExpr(op, DecimalToken(_, i)) if op.srcStr == "-" => Good(-(i.toFloat))
    /*  case FloatToken(_, _, d) => Good(d.toFloat)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "+" => Good(d.toFloat)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "-" => Good(-d.toFloat)
     */ case  _ => expr.exprParseErr[Float]
    }
  }
  

  implicit val ArrayIntImplicit: Persist[Array[Int]] = new PersistSeqLike[Int, Array[Int]](Show.intPersistImplicit)
  {
    override def showSemi(thisArray: Array[Int]): String = thisArray.map(evA.showComma(_)).semiFold
    override def showComma(thisArray: Array[Int]): String = thisArray.map(evA.show(_)).commaFold
    override def fromParameterStatements(sts: Refs[Statement]): EMon[Array[Int]] = TextPosn.empty.bad("ArrayInt from statements")
    override def fromClauses(clauses: Refs[Clause]): EMon[Array[Int]] = ???

    override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
    { case SemicolonToken(_) => Good(Array[Int]())
      case AlphaBracketExpr(IdentifierUpperToken(_, "Seq"), Refs2(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) => ???
        //sts.eMap[Int](_.errGet[Int](evA)).map(_.array)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  /** Implicit method for creating Arr[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrRefImplicit[A <: AnyRef](implicit ev: Persist[A]): Persist[ArrOld[A]] = new ArrRefPersist[A](ev)
  class ArrRefPersist[A <: AnyRef](ev: Persist[A]) extends PersistSeqLike[A, ArrOld[A]](ev)
  {
    override def showSemi(thisArr: ArrOld[A]): String = thisArr.map(ev.showComma(_)).semiFold
    override def showComma(thisArr: ArrOld[A]): String = thisArr.map(ev.show(_)).commaFold
    override def fromParameterStatements(sts: Refs[Statement]): EMon[ArrOld[A]] = ???
    override def fromClauses(clauses: Refs[Clause]): EMon[ArrOld[A]] = ???

    override def fromExpr(expr: ParseExpr): EMon[ArrOld[A]] =  expr match
    {
      case AlphaBracketExpr(IdentifierUpperToken(_, typeName), Refs1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentifierUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }
  }

  implicit def tuple2Implicit[A1, A2](implicit ev1: Persist[A1], ev2: Persist[A2], eq1: Eq[A1], eq2: Eq[A2]): Persist[Tuple2[A1, A2]] =
    Persist2[A1, A2, (A1, A2)]("Tuple2", "_1", _._1, "_2", _._2, (a1, a2) => (a1, a2))
}