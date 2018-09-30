package ldr
import scala.language.higherKinds
import scalaz.{Apply, Monoid, \/}
import scalaz.Id.Id
import scalaz.syntax.either._

trait LDRK9[F[_], A1, A2, A3, A4, A5, A6, A7, A8, A9] extends LDR {
  type Prod = (F[A1], F[A2], F[A3], F[A4], F[A5], F[A6], F[A7], F[A8], F[A9])
  type Cop = (F[A1] \/ (F[A2] \/ (F[A3] \/ (F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ (F[A8] \/ F[A9]))))))))
  def combine[G[_]](implicit a0: G[F[A1]], a1: G[F[A2]], a2: G[F[A3]], a3: G[F[A4]], a4: G[F[A5]], a5: G[F[A6]], a6: G[F[A7]], a7: G[F[A8]], a8: G[F[A9]]): ComposeLDR[G, Cop, Prod] =
    new ComposeLDR[G, Cop, Prod] {
      def mkChoose[B](f: B => Cop)(implicit d: Decidable[G]): G[B] =
        Combine.choose9(a0, a1, a2, a3, a4, a5, a6, a7, a8)(f)
      def mkAlt[B](f: Cop => B)(implicit a: Alt[G]): G[B] =
        Combine.altly9(a0, a1, a2, a3, a4, a5, a6, a7, a8)(f)
      def mkDivide[B](f: B => Prod)(implicit d: Divide[G]): G[B] =
        Combine.divide9(a0, a1, a2, a3, a4, a5, a6, a7, a8)(f)
      def mkApply[B](f: Prod => B)(implicit a: Apply[G]): G[B] =
        Combine.apply9(a0, a1, a2, a3, a4, a5, a6, a7, a8)((i0, i1, i2, i3, i4, i5, i6, i7, i8) => f((i0, i1, i2, i3, i4, i5, i6, i7, i8)))
    }

  object instances {

    implicit val inja0: Inj[Cop, F[A1]] =
      Inj.instance(_.left[(F[A2] \/ (F[A3] \/ (F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ (F[A8] \/ F[A9])))))))])

    implicit val inja1: Inj[Cop, F[A2]] =
      Inj.instance(_.left[(F[A3] \/ (F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ (F[A8] \/ F[A9]))))))].right[F[A1]])

    implicit val inja2: Inj[Cop, F[A3]] =
      Inj.instance(_.left[(F[A4] \/ (F[A5] \/ (F[A6] \/ (F[A7] \/ (F[A8] \/ F[A9])))))].right[F[A2]].right[F[A1]])

    implicit val inja3: Inj[Cop, F[A4]] =
      Inj.instance(_.left[(F[A5] \/ (F[A6] \/ (F[A7] \/ (F[A8] \/ F[A9]))))].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja4: Inj[Cop, F[A5]] =
      Inj.instance(_.left[(F[A6] \/ (F[A7] \/ (F[A8] \/ F[A9])))].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja5: Inj[Cop, F[A6]] =
      Inj.instance(_.left[(F[A7] \/ (F[A8] \/ F[A9]))].right[F[A5]].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja6: Inj[Cop, F[A7]] =
      Inj.instance(_.left[(F[A8] \/ F[A9])].right[F[A6]].right[F[A5]].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja7: Inj[Cop, F[A8]] =
      Inj.instance(_.left[F[A9]].right[F[A7]].right[F[A6]].right[F[A5]].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit val inja8: Inj[Cop, F[A9]] =
      Inj.instance(_.right[F[A8]].right[F[A7]].right[F[A6]].right[F[A5]].right[F[A4]].right[F[A3]].right[F[A2]].right[F[A1]])

    implicit def lifta0(implicit M: Monoid[Prod]): Inj[Prod, F[A1]] = {
      val (_, a0, a1, a2, a3, a4, a5, a6, a7) =
        M.zero
      Inj.instance((_, a0, a1, a2, a3, a4, a5, a6, a7))
    }

    implicit def lifta1(implicit M: Monoid[Prod]): Inj[Prod, F[A2]] = {
      val (a0, _, a1, a2, a3, a4, a5, a6, a7) =
        M.zero
      Inj.instance((a0, _, a1, a2, a3, a4, a5, a6, a7))
    }

    implicit def lifta2(implicit M: Monoid[Prod]): Inj[Prod, F[A3]] = {
      val (a0, a1, _, a2, a3, a4, a5, a6, a7) =
        M.zero
      Inj.instance((a0, a1, _, a2, a3, a4, a5, a6, a7))
    }

    implicit def lifta3(implicit M: Monoid[Prod]): Inj[Prod, F[A4]] = {
      val (a0, a1, a2, _, a3, a4, a5, a6, a7) =
        M.zero
      Inj.instance((a0, a1, a2, _, a3, a4, a5, a6, a7))
    }

    implicit def lifta4(implicit M: Monoid[Prod]): Inj[Prod, F[A5]] = {
      val (a0, a1, a2, a3, _, a4, a5, a6, a7) =
        M.zero
      Inj.instance((a0, a1, a2, a3, _, a4, a5, a6, a7))
    }

    implicit def lifta5(implicit M: Monoid[Prod]): Inj[Prod, F[A6]] = {
      val (a0, a1, a2, a3, a4, _, a5, a6, a7) =
        M.zero
      Inj.instance((a0, a1, a2, a3, a4, _, a5, a6, a7))
    }

    implicit def lifta6(implicit M: Monoid[Prod]): Inj[Prod, F[A7]] = {
      val (a0, a1, a2, a3, a4, a5, _, a6, a7) =
        M.zero
      Inj.instance((a0, a1, a2, a3, a4, a5, _, a6, a7))
    }

    implicit def lifta7(implicit M: Monoid[Prod]): Inj[Prod, F[A8]] = {
      val (a0, a1, a2, a3, a4, a5, a6, _, a7) =
        M.zero
      Inj.instance((a0, a1, a2, a3, a4, a5, a6, _, a7))
    }

    implicit def lifta8(implicit M: Monoid[Prod]): Inj[Prod, F[A9]] = {
      val (a0, a1, a2, a3, a4, a5, a6, a7, _) =
        M.zero
      Inj.instance((a0, a1, a2, a3, a4, a5, a6, a7, _))
    }

  }

  import instances._

  val injEv = combine[Inj.Aux[Cop]#Out].choose
  def liftEv(implicit M: Monoid[Prod]): Inj[Prod, Prod] = combine[Inj.Aux[Prod]#Out].divide

}

object LDRK9 {

  def apply[F[_], A1, A2, A3, A4, A5, A6, A7, A8, A9]: LDRK9[F, A1, A2, A3, A4, A5, A6, A7, A8, A9] =
    new LDRK9[F, A1, A2, A3, A4, A5, A6, A7, A8, A9] {}
}

trait LDRF9[A1, A2, A3, A4, A5, A6, A7, A8, A9] {
  type Repr[F[_]] = LDRK9[F, A1, A2, A3, A4, A5, A6, A7, A8, A9]
  def apply[F[_]]: Repr[F] =
    new LDRK9[F, A1, A2, A3, A4, A5, A6, A7, A8, A9] {}
}

object LDRF9 {
  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9]: LDRF9[A1, A2, A3, A4, A5, A6, A7, A8, A9] =
    new LDRF9[A1, A2, A3, A4, A5, A6, A7, A8, A9] {}
}

trait LDR9[A1, A2, A3, A4, A5, A6, A7, A8, A9] extends LDRK9[Id, A1, A2, A3, A4, A5, A6, A7, A8, A9]

object LDR9 {
  def apply[A1, A2, A3, A4, A5, A6, A7, A8, A9]: LDR9[A1, A2, A3, A4, A5, A6, A7, A8, A9] =
    new LDR9[A1, A2, A3, A4, A5, A6, A7, A8, A9] {}
}