/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.immutable.ArraySeq

trait ShowT[-T] extends TypeStr
{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  def strT(obj: T): String

  def showT(obj: T, style: ShowStyle): String

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  def syntaxDepthT(obj: T): Int
}


/* The companion object for the ShowT type class. Persist extends ShowT with UnShow. As its very unlikely that anyone would want to create an UnShow
   instance without a ShowT instance. Many Persist instances are placed inside the Show companion object. However type instances that themselves
   one or more Show type instances as parameters require a specific Show instance. The Persist instance for these types will require corresponding
   Persist type instances, and these will be placed in the Persist companion object. */
object ShowT
{
  implicit val intPersistImplicit: PersistDec[Int] = new PersistSimple[Int]("Int")
  {
    def strT(obj: Int): String = obj.toString

    override def fromExpr(expr: Expr): EMon[Int] = expr match {
      case IntStdToken(i) => Good(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "+" => Good(i)
      case PreOpExpr(op, IntStdToken(i)) if op.srcStr == "-" => Good(-i)
      case _ => expr.exprParseErr[Int]
    }
  }

  val hexadecimal: ShowDecT[Int] = new ShowSimpleT[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.hexStr
  }

  val base32: ShowDecT[Int] = new ShowSimpleT[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.base32
  }

  /** Implicit [[PersistDec]] instance / evidence for [[Double]]. */
  implicit val doublePersistEv: PersistDec[Double] = new PersistDec[Double]
  {
    override def typeStr: String = "DFloat"
    override def syntaxDepthT(obj: Double): Int = 1

    def strT(obj: Double): String = {
      val s1 = obj.toString
      ife(s1.last == '0', s1.dropRight(2), s1)
    }

    override def showDecT(obj: Double, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
    {
      val orig = obj.toString
      val len = orig.length
      val minPlacesAdj: Int = ife(maxPlaces < 0, minPlaces, minPlaces.min(maxPlaces))
      val dotIndex: Int = orig.indexOf('.')
      val fracLen: Int = len - dotIndex - 1

      val inner: String = None match
      { case _ if maxPlaces < 0 & minPlacesAdj <= 1 & orig.last == '0' => orig.dropRight(2)
        case _ if fracLen < minPlacesAdj => orig + (minPlacesAdj -fracLen).repeatChar('0')
        case _ if maxPlaces < 0 => orig
        case _ if maxPlaces == 0 => orig.dropRight(fracLen + 1)
        case _ if fracLen > maxPlaces => orig.dropRight(fracLen - maxPlaces)
        case _ => orig
      }

      style match {
        case ShowTyped => typeStr + inner.enParenth
        case _ => inner
      }
    }

    override def fromExpr(expr: Expr): EMon[Double] = expr match
    { case ValidFracToken(d) => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "+" => Good(d)
      case PreOpExpr(op, ValidFracToken(d)) if op.srcStr == "-" => Good(d)
      case _ => expr.exprParseErr[Double]
    }
  }

  implicit val longPersistImplicit: PersistDec[Long] = new PersistSimple[Long]("Long")
  { def strT(obj: Long): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Long] = expr match
    { case NatDeciToken(_, i) => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErr[Long]
    }
  }

  implicit val floatImplicit: ShowDecT[Float] = new ShowSimpleT[Float]
  { override def typeStr: String = "SFloat"
    def strT(obj: Float): String = obj.toString
  }

  implicit val booleanPersistImplicit: PersistDec[Boolean] = new PersistSimple[Boolean]("Bool")
  { override def strT(obj: Boolean): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Good(true)
      case IdentLowerToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  implicit val stringPersistImplicit: PersistDec[String] = new PersistSimple[String]("Str")
  { def strT(obj: String): String = obj.enquote
    override def fromExpr(expr: Expr): EMon[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErr[String]
    }
  }

  implicit val charImplicit: ShowDecT[Char] = new ShowSimpleT[Char]
  { override def typeStr: String = "Char"
    def strT(obj: Char): String = obj.toString.enquote1
  }

  class ShowIterableClass[A, R <: Iterable[A]](val evA: ShowDecT[A]) extends ShowIterable[A, R] with ShowDecT[R]{}

  implicit def ShowIterableImplicit[A](implicit evA: ShowDecT[A]): ShowDecT[Iterable[A]] = new ShowIterableClass[A, Iterable[A]](evA)
  implicit def ShowSeqImplicit[A](implicit evA: ShowDecT[A]): ShowDecT[Seq[A]] = new ShowIterableClass[A, Seq[A]](evA)

  /** Implicit method for creating List[A: Show] instances. */
  implicit def listImplicit[A](implicit ev: ShowDecT[A]): ShowDecT[List[A]] = new ShowIterableClass[A, List[A]](ev)

  /** Implicit method for creating ::[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  //implicit def consShowImplicit[A](implicit ev: ShowT[A]): ShowT[::[A]] = new PersistConsImplicit[A](ev)

  //implicit def nilPersistImplicit[A](implicit ev: Persist[A]): Persist[Nil.type] = new PersistNilImplicit[A](ev)

  implicit def vectorImplicit[A](implicit ev: ShowDecT[A]): ShowDecT[Vector[A]] = new ShowIterableClass[A, Vector[A]](ev)

  implicit val arrayIntImplicit: ShowDecT[Array[Int]] = new ShowTSeqLike[Int, Array[Int]]
  {
    override def evA: ShowDecT[Int] = ShowT.intPersistImplicit
    override def syntaxDepthT(obj: Array[Int]): Int = 2

    override def showDecT(obj: Array[Int], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = "Unimplemented"
  }

  class ArrRefPersist[A <: AnyRef](ev: PersistDec[A]) extends PersistSeqLike[A, ArraySeq[A]](ev)
  {
    override def syntaxDepthT(obj: ArraySeq[A]): Int = ???

    override def fromExpr(expr: Expr): EMon[ArraySeq[A]] =  expr match
    { case AlphaBracketExpr(IdentUpperToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showDecT(obj: ArraySeq[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist[A <: AnyRef](implicit ev: PersistDec[A]): PersistDec[Array[A]] = new ArrayRefPersist[A](ev)
  class ArrayRefPersist[A <: AnyRef](ev: PersistDec[A]) extends PersistSeqLike[A, Array[A]](ev)
  {
    override def syntaxDepthT(obj: Array[A]): Int = ???

    override def fromExpr(expr: Expr): EMon[Array[A]] =  expr match
    {
      case AlphaBracketExpr(IdentLowerToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
      case AlphaBracketExpr(IdentLowerToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
      case _ => ??? // expr.exprParseErr[A](this)
    }

    override def showDecT(obj: Array[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit method for creating Arr[A <: Show] instances. This seems toRich have to be a method rather directly using an implicit class */
  implicit def arraySeqImplicit[A](implicit ev: ShowDecT[A]): ShowDecT[collection.immutable.ArraySeq[A]] = new ShowTSeqLike[A, ArraySeq[A]]
  {
    override def syntaxDepthT(obj: ArraySeq[A]): Int = ???
    override def evA: ShowDecT[A] = ev

    /** Not fully correct yet. */
    override def showDecT(obj: ArraySeq[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String =
      obj.map(el => ev.showDecT(el, ShowStandard, maxPlaces, 0)).semiFold
  }

  implicit def somePersistImplicit[A](implicit ev: PersistDec[A]): PersistDec[Some[A]] = new PersistDec[Some[A]]
  {
    override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepthT(obj: Some[A]): Int = ev.syntaxDepthT(obj.value)
    override def strT(obj: Some[A]): String = ev.strT(obj.value)

    override def showDecT(obj: Some[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

    override def fromExpr(expr: Expr): EMon[Some[A]] = expr match
    { case AlphaBracketExpr(IdentUpperToken(_, "Some"), Arr1(ParenthBlock(Arr1(hs), _, _))) => ev.fromExpr(hs.expr).map(Some(_))
      case expr => ev.fromExpr(expr).map(Some(_))
    }
  }

  implicit val nonePersistImplicit: PersistDec[None.type] = new PersistSimple[None.type]("None")
  {
    override def strT(obj: None.type): String = ""

    def fromExpr(expr: Expr): EMon[None.type] = expr match
    { case IdentLowerToken(_, "None") => Good(None)
      case eet: EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }

  implicit def optionPersistImplicit[A](implicit evA: PersistDec[A]): PersistDec[Option[A]] =
    new PersistSum2[Option[A], Some[A], None.type](somePersistImplicit[A](evA), nonePersistImplicit)
    { override def typeStr: String = "Option" + evA.typeStr.enSquare
      override def syntaxDepthT(obj: Option[A]): Int = obj.fld(1, evA.syntaxDepthT(_))
    }
}

sealed trait ShowTInstancesPriority2
{ /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqPersistImplicit[T](implicit ev: PersistDec[T]): PersistDec[Seq[T]] = new PersistSeqImplicit[T](ev)
}

class ShowTExtensions[-A](ev: ShowT[A], thisVal: A)
{ /** Provides the standard string representation for the object. */
  @inline def str: String = ev.strT(thisVal)
}