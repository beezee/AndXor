package andxor
import scala.language.higherKinds
import scalaz.{Apply, Foldable, Functor, PlusEmpty, Monoid, \/, -\/, \/-, ~>}
import scalaz.Id.Id
import scalaz.syntax.either._

trait AndXorK8[F[_], A1, A2, A3, A4, A5, A6, A7, A8] extends AndXor {
  type Prod = (F[A1], F[A2], F[A3], F[A4], F[A5], F[A6], F[A7], F[A8])
  type Cop = (F[A1] \/ (F[A2] \/ (F[A3] \/ (F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ F[A8])))))))
  val AndXorF = AndXorF8[A1, A2, A3, A4, A5, A6, A7, A8]
  type AndXor[G[_]] = AndXorF8[A1, A2, A3, A4, A5, A6, A7, A8]#Repr[G]
  def combine[G[_]](implicit a0: G[F[A1]], a1: G[F[A2]], a2: G[F[A3]], a3: G[F[A4]], a4: G[F[A5]], a5: G[F[A6]], a6: G[F[A7]], a7: G[F[A8]]): ComposeAndXor[G, Cop, Prod] =
    new ComposeAndXor[G, Cop, Prod] {
      def mkChoose[B](f: B => Cop)(implicit d: Decidable[G]): G[B] =
        Combine.choose8(a0, a1, a2, a3, a4, a5, a6, a7)(f)
      def mkAlt[B](f: Cop => B)(implicit a: Alt[G]): G[B] =
        Combine.altly8(a0, a1, a2, a3, a4, a5, a6, a7)(f)
      def mkDivide[B](f: B => Prod)(implicit d: Divide[G]): G[B] =
        Combine.divide8(a0, a1, a2, a3, a4, a5, a6, a7)(f)
      def mkApply[B](f: Prod => B)(implicit a: Apply[G]): G[B] =
        Combine.apply8(a0, a1, a2, a3, a4, a5, a6, a7)((i0, i1, i2, i3, i4, i5, i6, i7) => f((i0, i1, i2, i3, i4, i5, i6, i7)))
    }

  object instances {

    implicit val inja0: Inj[Cop, F[A1]] =
      Inj.instance(_.left[(F[A2] \/ (F[A3] \/ (F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ F[A8]))))))])

    implicit val inja1: Inj[Cop, F[A2]] =
      Inj.instance(_.left[(F[A3] \/ (F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ F[A8])))))].right[F[A1]])

    implicit val inja2: Inj[Cop, F[A3]] =
      Inj.instance(_.left[(F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ F[A8]))))].right[F[A2]].right[F[A1]])

    implicit val inja3: Inj[Cop, F[A4]] =
      Inj.instance(_.left[(F[A5] \/ (F[A6] \/ (F[A7] \/ F[A8])))].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja4: Inj[Cop, F[A5]] =
      Inj.instance(_.left[(F[A6] \/ (F[A7] \/ F[A8]))].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja5: Inj[Cop, F[A6]] =
      Inj.instance(_.left[(F[A7] \/ F[A8])].right[F[A5]].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja6: Inj[Cop, F[A7]] =
      Inj.instance(_.left[F[A8]].right[F[A6]].right[F[A5]].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja7: Inj[Cop, F[A8]] =
      Inj.instance(_.right[F[A7]].right[F[A6]].right[F[A5]].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit def lifta0(implicit M: Monoid[Prod]): Inj[Prod, F[A1]] = {
      val (_, a0, a1, a2, a3, a4, a5, a6) =
        M.zero
      Inj.instance((_, a0, a1, a2, a3, a4, a5, a6))
    }

    implicit def lifta1(implicit M: Monoid[Prod]): Inj[Prod, F[A2]] = {
      val (a0, _, a1, a2, a3, a4, a5, a6) =
        M.zero
      Inj.instance((a0, _, a1, a2, a3, a4, a5, a6))
    }

    implicit def lifta2(implicit M: Monoid[Prod]): Inj[Prod, F[A3]] = {
      val (a0, a1, _, a2, a3, a4, a5, a6) =
        M.zero
      Inj.instance((a0, a1, _, a2, a3, a4, a5, a6))
    }

    implicit def lifta3(implicit M: Monoid[Prod]): Inj[Prod, F[A4]] = {
      val (a0, a1, a2, _, a3, a4, a5, a6) =
        M.zero
      Inj.instance((a0, a1, a2, _, a3, a4, a5, a6))
    }

    implicit def lifta4(implicit M: Monoid[Prod]): Inj[Prod, F[A5]] = {
      val (a0, a1, a2, a3, _, a4, a5, a6) =
        M.zero
      Inj.instance((a0, a1, a2, a3, _, a4, a5, a6))
    }

    implicit def lifta5(implicit M: Monoid[Prod]): Inj[Prod, F[A6]] = {
      val (a0, a1, a2, a3, a4, _, a5, a6) =
        M.zero
      Inj.instance((a0, a1, a2, a3, a4, _, a5, a6))
    }

    implicit def lifta6(implicit M: Monoid[Prod]): Inj[Prod, F[A7]] = {
      val (a0, a1, a2, a3, a4, a5, _, a6) =
        M.zero
      Inj.instance((a0, a1, a2, a3, a4, a5, _, a6))
    }

    implicit def lifta7(implicit M: Monoid[Prod]): Inj[Prod, F[A8]] = {
      val (a0, a1, a2, a3, a4, a5, a6, _) =
        M.zero
      Inj.instance((a0, a1, a2, a3, a4, a5, a6, _))
    }

  }

  import instances._

  val injEv = combine[Inj.Aux[Cop]#Out].choose
  def liftEv(implicit M: Monoid[Prod]): Inj[Prod, Prod] = combine[Inj.Aux[Prod]#Out].divide

  def transformP[G[_]](nt: (F ~> G)): AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8]#Prod => AndXorK8[G, A1, A2, A3, A4, A5, A6, A7, A8]#Prod =
    (p: AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8]#Prod) => (nt(p._1), nt(p._2), nt(p._3), nt(p._4), nt(p._5), nt(p._6), nt(p._7), nt(p._8))

  def transformC[G[_]](nt: (F ~> G)): AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8]#Cop => AndXorK8[G, A1, A2, A3, A4, A5, A6, A7, A8]#Cop =
    (p: AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8]#Cop) => p.bimap(nt(_), _.bimap(nt(_), _.bimap(nt(_), _.bimap(nt(_), _.bimap(nt(_), _.bimap(nt(_), _.bimap(nt(_), nt(_))))))))

  // format: off
  def sequenceP(prod: Prod)(implicit A: Apply[F]): F[AndXorK8[Id, A1, A2, A3, A4, A5, A6, A7, A8]#Prod] = {
    val (a0, a1, a2, a3, a4, a5, a6, a7) = prod
    A.ap(a7)(
    A.ap(a6)(
    A.ap(a5)(
    A.ap(a4)(
    A.ap(a3)(
    A.ap(a2)(
    A.ap(a1)(
     A.map(a0)(((i0: A1, i1: A2, i2: A3, i3: A4, i4: A5, i5: A6, i6: A7, i7: A8) =>
    (i0, i1, i2, i3, i4, i5, i6, i7)).curried))))))))
  }
  

  def foldMap[G[_], C](p: AndXor[G]#Prod)(
    map: AndXor[Id]#Cop => C)(
    implicit O: Ordering[AndXorK8[Id, A1, A2, A3, A4, A5, A6, A7, A8]#Cop], M: Monoid[C],
    PE: PlusEmpty[G], U: Uncons[G]): C = {
    val TG = AndXorF[G]
    val TI = AndXorF[Id]
    import scala.collection.mutable.{PriorityQueue => PQ}
    import TI.instances._
    def uncons(p: TG.Prod): (List[TI.Cop], TG.Prod) = {
     val hts = (U(p._1), U(p._2), U(p._3), U(p._4), U(p._5), U(p._6), U(p._7), U(p._8))
     (List(hts._1._1.map(TI.inj(_: A1)), hts._2._1.map(TI.inj(_: A2)), hts._3._1.map(TI.inj(_: A3)), hts._4._1.map(TI.inj(_: A4)), hts._5._1.map(TI.inj(_: A5)), hts._6._1.map(TI.inj(_: A6)), hts._7._1.map(TI.inj(_: A7)), hts._8._1.map(TI.inj(_: A8))).flatten,
      (hts._1._2, hts._2._2, hts._3._2, hts._4._2, hts._5._2, hts._6._2, hts._7._2, hts._8._2))
    }
    @scala.annotation.tailrec
    def go(prod: TG.Prod, q: PQ[TI.Cop], out: C): C =
     (prod.==((PE.empty[A1], PE.empty[A2], PE.empty[A3], PE.empty[A4], PE.empty[A5], PE.empty[A6], PE.empty[A7], PE.empty[A8]))) match {
       case true =>
         q.foldLeft(out)((acc, el) => M.append(acc, map(el)))
       case false => q.isEmpty match {
         case true => {
           val (hs, ts) = uncons(prod)
           q ++= hs
           go(ts, q, out)
         }
         case false => q.dequeue match {
                        case -\/(x) => {
               val (h, t) = U(prod._1)
               go((t, prod._2, prod._3, prod._4, prod._5, prod._6, prod._7, prod._8),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }
             case \/-(-\/(x)) => {
               val (h, t) = U(prod._2)
               go((prod._1, t, prod._3, prod._4, prod._5, prod._6, prod._7, prod._8),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }
             case \/-(\/-(-\/(x))) => {
               val (h, t) = U(prod._3)
               go((prod._1, prod._2, t, prod._4, prod._5, prod._6, prod._7, prod._8),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }
             case \/-(\/-(\/-(-\/(x)))) => {
               val (h, t) = U(prod._4)
               go((prod._1, prod._2, prod._3, t, prod._5, prod._6, prod._7, prod._8),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }
             case \/-(\/-(\/-(\/-(-\/(x))))) => {
               val (h, t) = U(prod._5)
               go((prod._1, prod._2, prod._3, prod._4, t, prod._6, prod._7, prod._8),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }
             case \/-(\/-(\/-(\/-(\/-(-\/(x)))))) => {
               val (h, t) = U(prod._6)
               go((prod._1, prod._2, prod._3, prod._4, prod._5, t, prod._7, prod._8),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }
             case \/-(\/-(\/-(\/-(\/-(\/-(-\/(x))))))) => {
               val (h, t) = U(prod._7)
               go((prod._1, prod._2, prod._3, prod._4, prod._5, prod._6, t, prod._8),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }
             case \/-(\/-(\/-(\/-(\/-(\/-(\/-(x))))))) => {
               val (h, t) = U(prod._8)
               go((prod._1, prod._2, prod._3, prod._4, prod._5, prod._6, prod._7, t),
                 q ++= h.map(TI.inj(_)), M.append(out, map(TI.inj(x))))
             }

         }
       }
     }
    val Q = new scala.collection.mutable.PriorityQueue[TI.Cop]()
    val (hs, ts) = uncons(p)
    Q ++= hs
    go(ts, Q, M.zero)
  }
  // format: on
}

object AndXorK8 {

  def apply[F[_], A1, A2, A3, A4, A5, A6, A7, A8]: AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8] =
    new AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8] {}
}

trait AndXorF8[A1, A2, A3, A4, A5, A6, A7, A8] {
  type Repr[F[_]] = AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8]
  def apply[F[_]]: Repr[F] =
    new AndXorK8[F, A1, A2, A3, A4, A5, A6, A7, A8] {}
}

object AndXorF8 {
  def apply[A1, A2, A3, A4, A5, A6, A7, A8]: AndXorF8[A1, A2, A3, A4, A5, A6, A7, A8] =
    new AndXorF8[A1, A2, A3, A4, A5, A6, A7, A8] {}
}

trait AndXor8[A1, A2, A3, A4, A5, A6, A7, A8] extends AndXorK8[Id, A1, A2, A3, A4, A5, A6, A7, A8]

object AndXor8 {
  def apply[A1, A2, A3, A4, A5, A6, A7, A8]: AndXor8[A1, A2, A3, A4, A5, A6, A7, A8] =
    new AndXor8[A1, A2, A3, A4, A5, A6, A7, A8] {}

}
