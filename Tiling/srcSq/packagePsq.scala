/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** Package for square tile grids. */
package object psq
{
  /** The square centre step values. */
  val scSteps: RArr[SqStep] = RArr(SqUp, SqUR, SqRt, SqDR, SqDn, SqDL, SqLt, SqUL)

  implicit class IntGridImplicit(thisInt: Int)
  { /** Syntax for succinct [[SqCen]] notation. */
    def sc (c: Int): SqCen = SqCen(thisInt, c)

    /** Syntax for succinct [[SqSep]] notation. */
    def ss (c: Int): SqSep = SqSep(thisInt, c)
  }
  implicit class ArrExtensions(thisArr: RArr[AnyRef])
  { /** Finds the first [[SqCen]] in this sequence */
    def sqCenFind: Option[SqCen] =
    {
      var res: Option[SqCen] = None
      var count = 0
      while (res.isEmpty & count < thisArr.length) thisArr(count) match {
        case sc: SqCen => res = Some(sc)
        case _ => count += 1
      }
      res
    }

    /** Finds the first [[SqCen]] in this sequence and performs the side effecting function on it. */
    def sqCenForFirst(f: SqCen => Unit): Unit =
    { var count = 0
      while (count < thisArr.length) thisArr(count) match
      { case sc: SqCen => { f(sc); count = thisArr.length }
        case _ => count += 1
      }
    }
  }

  implicit class RArrAnysExtensions(thisArr: RArr[Any])
  { /** Finds the first [[SqCen]] in this sequence */
    def sqCenFind: Option[SqCen] =
    { var res: Option[SqCen] = None
      var count = 0
      while (res.isEmpty & count < thisArr.length) thisArr(count) match
      { case sc: SqCen => res = Some(sc)
        case _ => count += 1
      }
      res
    }

    /** Finds the first [[SqCen]] in this sequence and performs the side effecting function on it. */
    def sqCenForFirst(f: SqCen => Unit): Unit = {
      var count = 0
      while (count < thisArr.length) thisArr(count) match {
        case sc: SqCen => {
          f(sc); count = thisArr.length
        }
        case _ => count += 1
      }
    }
  }
}