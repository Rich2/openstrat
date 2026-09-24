/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.annotation.unchecked.uncheckedVariance, pParse.*, reflect.ClassTag, collection.mutable.ArrayBuffer

/** Extension methods for [[Either]]. */
implicit class EitherExts[E, A](val thisEither: Either[E, A])
{
  def fold[B](fe: E => B)(fa: A => B): B = thisEither match
  { case Left[E, A](err) => fe(err)
    case Right(value) => fa(value)
  }

  /** Returns this if success, else returns the other [[Either]]. */
  def succOrOther[EE >: E <: Throwable, AA >: A](otherEither: => Either[EE, AA] @uncheckedVariance): Either[EE, AA] = thisEither match {
    case Right(_) => thisEither
    case Left(_) => otherEither
  }

  /** Classic flatMap function taking a function from A => [[Option]][B] rather than the standard [[Either]] of B. */
  def flatOptMap[B](f: A => Option[B]): Either[E | ExcNotFound.type, B] = thisEither match
  { case Right(value) => f(value).fld(NotFoundLeft, b => Right(b))
    case Left(err) => Left(err)
  }
}

implicit class Eitherthrowable[E <: Throwable, A](thisEither: Either[E, A])
{ /** If this [[Either]] is a [[Right]] produce [[ErrBiAcc]] with the parameter function. If this is [[Left]] produce [[ErrBiAcc]] with this single [[Left]]. */
  def mapAcc[EE >: E <: Throwable, B](f: A => ErrBiAcc[EE, B])(using ctE: ClassTag[EE] @uncheckedVariance, ctB: ClassTag[B] @uncheckedVariance):
    ErrBiAcc[EE, B] = thisEither match
  { case Right(value) => f(value)
    case Left(err) => new ErrBiAcc[EE, B](Array[EE](err), Array[B]())
  }
  
  def reportStr: String = thisEither match
  { case Right(value) => value match
    { case er: EffectReport => er.reportStr
      case a => a.toString
    }
    case Left(err) => err.toString
  }
}

implicit class EitherStringExts[E <: Exception](thisEither: Either[E, String])
{/** Extension method two map this [[Either]] String to find a value of the given type from the String parsed as RSON. */
  def findType[A](using ev: Unshow[A]): Either[ParseException | E, A] = thisEither.flatMap(str => stringToStatements(str).flatMap(_.findType[A]))

  /** Extension method to map this [[Either]] String to find a value of the given type from the String parsed as RSON or return the elseValue if that fails. */
  def findTypeElse[A](elseValue: => A)(using ev: Unshow[A]): A = findType[A].getOrElse(elseValue)

  /** Extension method to map this [[Either]] String to find a value of the given type from the String parsed as RSON and then perform a foreach on the value
   * if successful. */
  def findTypeForeach[A: Unshow](f: A => Unit): Unit = findType[A].foreach(f)

  def findSetting[A](settingStr: String)(using ev: Unshow[A]): Either[ParseException | E, A] =
  { val res1: Either[ParseException | E, RArr[Statement]] = thisEither.flatMap(str => stringToStatements(str))
    res1.flatMap(_.findSetting[A](settingStr))
  }

  def findSettingElse[A: Unshow](settingStr: String, elseValue: => A): A = findSetting[A](settingStr).getOrElse(elseValue)
}

extension (obj: Either.type)
{ /** Extension method to collect errors. */
  def collectLefts[E, A](eithers: Either[E, A]*)(using ClassTag[E]): RArr[E] =
  { val buff = ArrayBuffer[E]()
    eithers.foreach{
      case Left(err) => buff.append(err)
      case _ =>
    }
    buff.toRArr
  }

  /** If both [[Either]] inputs are [[Right]]s return [[Right]] of function. Else return a [[Left]] [[ErrMulti]]. */
  def map2[EE, E <: EE, ME <: ErrMulti[E] & EE, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => B)(using builder: ErrMultiBuilder[EE, E, ME],
    ct: ClassTag[E]): Either[ME, B] = eb1 match
  { case Right(a1) => eb2.match
    { case Right(a2) => Right(f(a1, a2))
      case Left(err) => Left(builder.multi(err))
    }
    case Left(err1) => eb2 match
    { case Right(_) => Left(builder.multi(err1))
      case Left(err2) => Left(builder.multi(err1, err2))
    }
  }

  /** If both [[Either]] inputs are [[Right]]s, returns the result of the function. If one or both are [[Left]]s combines the errors. */
  def flatMap2[EE, E <: EE, ME <: ErrMulti[E] & EE, E2, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => Either[E2, B])(using
    builder: ErrMultiBuilder[EE, E, ME], ct: ClassTag[E]): Either[ME | E2 , B] = (eb1, eb2) match
  { case (Right(a1), Right(a2)) => f(a1, a2)
    case (Right(_), Left(err2)) => Left(builder.multi(err2))
    case (Left(err1), Right(_)) => Left(builder.multi(err1))
    case (Left(err1), Left(err2)) => Left(builder.multi(err1, err2))
  }

  /** If this [[Either]] is a [[Right]] produce [[ErrBiAcc]] with the parameter function. If this is [[Left]] produce [[ErrBiAcc]] with this single [[Left]]. */
  def map2Acc[E <: Throwable, A1, A2, B](eb1: Either[E, A1], eb2: Either[E, A2])(f: (A1, A2) => ErrBiAcc[E, B])(using ctE: ClassTag[E] @uncheckedVariance,
    ctB: ClassTag[B] @uncheckedVariance): ErrBiAcc[E, B] = eb1 match
  { case Right(a1) => eb2 match
    { case Right(a2) => f(a1, a2)
      case Left(e2) => ErrBiAcc.err1(e2)
    }
    case Left(e1) => eb2 match
    { case Right(_) => ErrBiAcc.err1(e1)
      case Left(e2) => ErrBiAcc.errs2(e1, e2)
    }
  }

  /** If all 3 [[Either]] inputs are [[Right]]s return [[Right]] of function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def map3[EE, E <: EE, ME <: ErrMulti[E] & EE, A1, A2, A3, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3])(f: (A1, A2, A3) => B)(
    using builder: ErrMultiBuilder[EE, E, ME], ct: ClassTag[E]): Either[ME, B] = (eb1, eb2, eb3) match
  { case (Right(a1), Right(a2), Right(a3)) => Right(f(a1, a2, a3))
    case _ => Left(builder.multi(collectLefts(eb1, eb2, eb3)))
  }
  
  /** If all 4 [[Either]] inputs are [[Right]]s return [[Right]] of function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def map4[EE, E <: EE, ME <: ErrMulti[E] & EE, A1, A2, A3, A4, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4])(
    f: (A1, A2, A3, A4) => B)(using builder: ErrMultiBuilder[EE, E, ME], ct: ClassTag[E]): Either[ME, B] = (eb1, eb2, eb3, eb4) match
  { case (Right(a1), Right(a2), Right(a3), Right(a4)) => Right(f(a1, a2, a3, a4))
    case _ => Left(builder.multi(collectLefts(eb1, eb2, eb3, eb4)))
  }
  /** If all 5 [[Either]] inputs are [[Right]]s return [[Right]] of function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def map5[EE, E <: EE, ME <: ErrMulti[E] & EE, A1, A2, A3, A4, A5, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4],
    eb5: Either[E, A5])(f: (A1, A2, A3, A4, A5) => B)(using builder: ErrMultiBuilder[EE, E, ME], ct: ClassTag[E]): Either[ME, B] =
   (eb1, eb2, eb3, eb4, eb5) match 
  { case (Right(a1), Right(a2), Right(a3), Right(a4), Right(a5)) => Right(f(a1, a2, a3, a4, a5))
    case _ => Left(builder.multi(collectLefts(eb1, eb2, eb3, eb4, eb5)))
  }
  
  /** If all 6 [[Either]] inputs are [[Right]]s return [[Right]] of function. If both [[Left]]s combine the errors. Error type may widen to contain all the
   * possibilities */
  def map6[EE, E <: EE, ME <: ErrMulti[E] & EE, A1, A2, A3, A4, A5, A6, B](eb1: Either[E, A1], eb2: Either[E, A2], eb3: Either[E, A3], eb4: Either[E, A4],
    eb5: Either[E, A5], eb6: Either[E, A6])(f: (A1, A2, A3, A4, A5, A6) => B)(using builder: ErrMultiBuilder[EE, E, ME], ct: ClassTag[E]): Either[ME, B] =
    (eb1, eb2, eb3, eb4, eb5, eb6) match
  { case (Right(a1), Right(a2), Right(a3), Right(a4), Right(a5), Right(a6)) => Right(f(a1, a2, a3, a4, a5, a6))
    case _ => Left(builder.multi(collectLefts(eb1, eb2, eb3, eb4, eb5)))
  }

  /** Folds over 2 [[Either]]s. Takes 2 functions the first is used one or both [[Either]]s are [[Left]]s. The second function is only used if both are
   * [[Right]]s. */
  def fold2[E <: Exception, A1, A2, B](eth1: => Either[E, A1], eth2: => Either[E, A2])(fe: ExcMulti[E] => B)(fa: (A1, A2) => B)(using ctE: ClassTag[E]):
  B = eth1 match {
    case Right(a1) => eth2 match {
      case Right(a2) => fa(a1, a2)
      case Left(err2) => fe(ExcMulti(err2))
    }
    case Left(err1) => eth2 match {
      case Right(_) => fe(ExcMulti(err1))
      case Left(err2) => fe(ExcMulti(err1, err2))
    }
  }

  /** Folds over 2 [[Either]]s but the functions return [[Unit]]. Takes 2 functions the first is used if one or both [[Either]]s are [[Left]]s. The second
   * function is only used if both are [[Right]]s. */
  def forboth2[E <: Exception, A1, A2, U1, U2](eth1: Either[E, A1], eth2: Either[E, A2])(fe: ExcMulti[E] => U1)(fa: (A1, A2) => U2)(using ctE: ClassTag[E]):
  Unit = eth1 match
  { case Right(a1) => eth2 match
    { case Right(a2) => fa(a1, a2)
      case Left(err2) => fe(ExcMulti(err2))
    }
    case Left(err1) => eth2 match
    { case Right(_) => fe(ExcMulti(err1))
      case Left(err2) => fe(ExcMulti(err1, err2))
    }
  }  
}

/** Extension class for [[Exception]] bifunctor for [[Tuple2]]s. */
extension [E <: Throwable, A1, A2](thisEE2: throwEitherT2[E, A1, A2])
{
  def t2FlatMap[B1, B2](f: (A1, A2) => throwEitherT2[E, B1, B2]): throwEitherT2[E, B1, B2] = thisEE2 match
  { case Succ2(a1, a2) => f(a1, a2)
    case Left(err) => Left(err)
    case eb => excep(s"$eb This case was unexpected")
  }
}