/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import java.io.IOException, annotation.unchecked.uncheckedVariance, reflect.ClassTag

/** Report of a successfully completed effect. Used for pure side effects that return [[Unit]]. */
trait EffectReport
{ /** The type off effect completed. */
  def reportTypeStr: String
  
  /** The specific detail of the effect completed. */
  def detailStr: String

  /** Report of the successful action / effect. */
  def reportStr: String
}

/** [[Show]] type class instances for [[EffectReport]]. Their main purpose is not to [[Persist]] [[EffectReport]]s but to be able to accumulate and summarise
 * them. */
trait ShowEffectReport[A <: EffectReport] extends ShowSimple[A]
{ /** Describes the action of the successful effect that is being reported. */
  def actionStr(numSuccesses: Int): String
}

/** Report of successful side effect. */
trait DoneIO extends EffectReport