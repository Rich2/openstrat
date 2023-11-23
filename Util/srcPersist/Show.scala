/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.immutable.ArraySeq

/** Common super trait for [[TellDec]], [[Show]] and [[Unshow]]. All of which inherit the typeStr property. */
trait PersistBase extends Any
{ /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
 * Show. */
  def typeStr: String
}

/** A type class for string, text and visual representation of objects. An alternative to toString. This trait has mor demanding ambitions Mostly you
 *  will want to use  Persist which not only gives the Show methods to String representation, but the methods to parse Strings back to objects of the
 *  type T. However it may often be useful to start with Show type class and upgrade it later to Persist[T]. The capabilities of decimal place
 *  precision and explicit typing for numbers are placed defined here and in the corresponding [[SHow]] type class although they have n meaning /
 *  purpose for many types, as separating them adds enormous complexity for very little gain. */
trait Show[-T] extends PersistBase
{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  def strT(obj: T): String

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  def syntaxDepth(obj: T): Int

  def show(obj: T, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = -1): String

  override def toString: String = "Show" + typeStr
}

/* The companion object for the ShowT type class. Persist extends ShowT with UnShow. As its very unlikely that anyone would want to create an UnShow
   instance without a ShowT instance. Many Persist instances are placed inside the Show companion object. However type instances that themselves
   one or more Show type instances as parameters require a specific Show instance. The Persist instance for these types will require corresponding
   Persist type instances, and these will be placed in the Persist companion object. */
object Show
{ /** Implicit [[Show]] type class instance / evidence for [[Int]]. */
  implicit val intEv: Show[Int] = ShowSimple("Int", _.toString)

  val hexadecimal: Show[Int] = new ShowSimple[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.hexStr
  }

  val base32: Show[Int] = new ShowSimple[Int]
  { override def typeStr: String = "Int"
    override def strT(obj: Int): String = obj.base32
  }

  /** Implicit [[Persist]] instance / evidence for [[Double]]. */
  implicit val doublePersistEv: Show[Double] = new Show[Double]
  {
    override def typeStr: String = "DFloat"
    override def syntaxDepth(obj: Double): Int = 1

    def strT(obj: Double): String =
    { val s1 = obj.toString
      ife(s1.last == '0', s1.dropRight(2), s1)
    }

    override def show(obj: Double, style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = -1): String =
    { val orig = obj.toString
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

      style match
      { case ShowTyped => typeStr + inner.enParenth
        case _ => inner
      }
    }
  }

  /** Implicit [[Show]] type class instance / evidence for [[Long]]. */
  implicit val longEv: Show[Long] = ShowSimple[Long]("Long", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[Long]]. */
  implicit val floatEv: Show[Float] = ShowSimple[Float]("SFloat", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[Boolean]]. */
  implicit val booleanEv: Show[Boolean] = ShowSimple[Boolean]("Bool", _.toString)

  /** Implicit [[Show]] type class instance / evidence for [[String]]. */
  implicit val stringEv: Show[String] = ShowSimple[String]("Str", _.enquote)

  /** Implicit [[Show]] type class instance / evidence for [[Char]]. */
  implicit val charEv: Show[Char] = ShowSimple[Char]("Char", _.toString.enquote1)

  class ShowIterableClass[A, R <: Iterable[A]](val evA: Show[A]) extends ShowIterable[A, R] with Show[R]{}

  implicit def ShowIterableImplicit[A](implicit evA: Show[A]): Show[Iterable[A]] = new ShowIterableClass[A, Iterable[A]](evA)
  implicit def ShowSeqImplicit[A](implicit evA: Show[A]): Show[Seq[A]] = new ShowIterableClass[A, Seq[A]](evA)

  /** Implicit method for creating List[A: Show] instances. */
  implicit def listImplicit[A](implicit ev: Show[A]): Show[List[A]] = new ShowIterableClass[A, List[A]](ev)

  implicit def vectorImplicit[A](implicit ev: Show[A]): Show[Vector[A]] = new ShowIterableClass[A, Vector[A]](ev)

  implicit val arrayIntImplicit: Show[Array[Int]] = new ShowSeq[Int, Array[Int]]
  { override def evA: Show[Int] = Show.intEv
    override def syntaxDepth(obj: Array[Int]): Int = 2
    override def showForeach(obj: Array[Int], f: Int => Unit): Unit = obj.foreach(f)
  }

  /** Implicit method for creating Arr[A <: Show] instances. This seems toRich have to be a method rather directly using an implicit class */
  implicit def arraySeqImplicit[A](implicit ev: Show[A]): Show[collection.immutable.ArraySeq[A]] = new ShowSeq[A, ArraySeq[A]]
  { override def syntaxDepth(obj: ArraySeq[A]): Int = ???
    override def evA: Show[A] = ev
    override def showForeach(obj: ArraySeq[A], f: A => Unit): Unit = obj.foreach(f)
  }

  /** [[Show]] type class instance evidence for [[Some]]. */
  implicit def someEv[A](implicit ev: Show[A]): Show[Some[A]] = new  Show[Some[A]]
  { override def typeStr: String = "Some" + ev.typeStr.enSquare
    override def syntaxDepth(obj: Some[A]): Int = ev.syntaxDepth(obj.value)
    override def strT(obj: Some[A]): String = ev.strT(obj.value)

    override def show(obj: Some[A], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** [[Show]] type class instance evidence for [[None.type]]. */
  implicit val noneEv: Show[None.type] = ShowSimple[None.type]("None", _ => "")

  /** [[Show]] type class instance evidence for [[Option]]. */
  implicit def optionEv[A](implicit evA: Show[A]): Show[Option[A]] =
    ShowSum2[Option[A], Some[A], None.type]("Opt", someEv[A](evA), noneEv)
}

/** Extension methods for types with [[Show]] type class instances. */
class ShowingExtensions[-A](ev: Show[A], thisVal: A)
{ /** Provides the standard string representation for the object. */
  @inline def str: String = ev.strT(thisVal)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def show(style: ShowStyle, decimalPlaces: Int = -1, minPlaces: Int = 0): String = ev.show(thisVal, style, decimalPlaces, minPlaces)

  /** Return the defining member values of the type as a series of comma separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 3. if it is 3 or greater return the full typed data. */
  @inline def strComma: String = ev.show(thisVal, ShowCommas, -1, 0)

  def str2Comma: String = ev.show(thisVal, ShowCommas, 2, 0)

  /** Return the defining member values of the type as a series of semicolon separated values without enclosing type information, note this will only
   *  happen if the syntax depth is less than 4. if it is 4 or greater return the full typed data. This method is not commonly needed but is useful
   *  for case classes with a single member. */
  @inline def strSemi: String = ev.show(thisVal, ShowSemis, -1, 0)

  @inline def strSemi(maxPlaces: Int, minPlaces: Int = 0): String =  ev.show(thisVal, ShowSemis, maxPlaces, minPlaces)

  def strMin: String = ev.show(thisVal, ShowMinimum)

  /** For most objects showTyped will return the same value as persist, for PeristValues the value will be type enclosed. 4.showTyped
   * will return Int(4). */
  @inline def strTyped: String = ev.show(thisVal, ShowTyped, -1, 0)

  def str0: String = ev.show(thisVal, ShowStandard, 0, 0)
  def str1: String = ev.show(thisVal, ShowStandard, 1, 0)
  def str2: String = ev.show(thisVal, ShowStandard, 2, 0)
  def str3: String = ev.show(thisVal, ShowStandard, 3, 0)

  /** Shows this object with field names. */
  def showFields: String = ev.show(thisVal, ShowFieldNames, -1, 0)

  def showTypedFields: String = ev.show(thisVal, ShowStdTypedFields, -1, 0)

  def showDepth: Int = ev.syntaxDepth(thisVal)
}

/** Show trait for Compound types contain elements, requiring the Show class or classes for the type or types of the constituent elements. */
trait ShowCompound[R] extends Show[R]
{ override def strT(obj: R): String = show(obj, ShowStandard)//, -1, 0)
}