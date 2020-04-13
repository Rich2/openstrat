/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug

case class ZPlayer(polities: Arr[Polity])

object PlayBritain extends ZPlayer (Arr(Britain))
object PlayGermany extends ZPlayer (Arr(Germany))
object PlayGermanyBritain extends ZPlayer (Arr(Germany, Britain))
object PlayGermanyFrance extends ZPlayer (Arr(Germany, France))