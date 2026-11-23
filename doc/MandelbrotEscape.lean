import Mathlib

/-!
# Mandelbrot/Julia escape criterion (Falk's epsilon proof)

This is a Lean 4/Mathlib transcription of the proof in
`doc/mandelbrot-iteration-condition.tex`.

The exponent q is real (not necessarily integral), and complex exponentiation
uses Mathlib's principal branch.
-/

noncomputable section

open Filter

namespace MandelbrotEscape

/-- The divergence radius 2^(1/(q-1)). -/
def radius (q : ℝ) : ℝ := (2 : ℝ) ^ ((q - 1)⁻¹)

/-- The two safety margins combined into one function. -/
def epsilon (c : ℂ) (q x : ℝ) : ℝ :=
  min (x ^ (q - 1) - 2) (1 - ‖c‖ / x)

/-- The orbit of z under w ↦ w^q + c. -/
def orbit (c : ℂ) (q : ℝ) (z : ℂ) : ℕ → ℂ
  | 0 => z
  | n + 1 => (orbit c q z n) ^ (q : ℂ) + c

private theorem radius_pos (q : ℝ) : 0 < radius q := by
  unfold radius
  exact Real.rpow_pos_of_pos (by norm_num : (0 : ℝ) < 2) _

private theorem radius_pow (q : ℝ) (hq : 1 < q) :
    (radius q) ^ (q - 1) = 2 := by
  have hne : q - 1 ≠ 0 := by linarith
  unfold radius
  rw [← Real.rpow_mul (by norm_num : (0 : ℝ) ≤ 2) ((q - 1)⁻¹) (q - 1)]
  rw [inv_mul_cancel₀ hne]
  simp

private theorem pow_gt_two (q : ℝ) (hq : 1 < q) {x : ℝ}
    (hx : radius q < x) : 2 < x ^ (q - 1) := by
  calc
    (2 : ℝ) = (radius q) ^ (q - 1) := (radius_pow q hq).symm
    _ < x ^ (q - 1) :=
      Real.rpow_lt_rpow (le_of_lt (radius_pos q)) hx (by linarith)

private theorem epsilon_pos (c : ℂ) (q x : ℝ)
    (hx : 0 < x) (hp : 2 < x ^ (q - 1)) (hc : ‖c‖ < x) :
    0 < epsilon c q x := by
  unfold epsilon
  apply lt_min_iff.mpr
  constructor
  · linarith
  · have hdiv : ‖c‖ / x < 1 := (div_lt_one hx).mpr hc
    linarith

/-- The margin cannot decrease as the modulus increases. -/
private theorem epsilon_mono (c : ℂ) (q : ℝ) (hq : 1 < q)
    {x y : ℝ} (hx : 0 < x) (hxy : x ≤ y) :
    epsilon c q x ≤ epsilon c q y := by
  unfold epsilon
  apply min_le_min
  · have hpow := Real.rpow_le_rpow (le_of_lt hx) hxy
        (by linarith : 0 ≤ q - 1)
    linarith
  · have hdiv : ‖c‖ / y ≤ ‖c‖ / x :=
      div_le_div_of_nonneg_left (norm_nonneg c) hx hxy
    linarith

/-- Equation (1): a strict additive increment beyond the escape radius. -/
private theorem one_step (c : ℂ) (q : ℝ) (hq : 1 < q)
    (w : ℂ) (hr : radius q < ‖w‖) (hc : ‖c‖ < ‖w‖) :
    ‖w‖ + 2 * radius q * epsilon c q ‖w‖ < ‖w ^ (q : ℂ) + c‖ := by
  have hwpos : 0 < ‖w‖ := (radius_pos q).trans hr
  have hp : 2 < ‖w‖ ^ (q - 1) := pow_gt_two q hq hr
  have hepos : 0 < epsilon c q ‖w‖ := epsilon_pos c q ‖w‖ hwpos hp hc
  -- Reverse triangle inequality, followed by the norm identity for complex powers.
  have htriangle : ‖w‖ ^ q ≤ ‖w ^ (q : ℂ) + c‖ + ‖c‖ := by
    have h := norm_sub_le (w ^ (q : ℂ) + c) c
    simpa only [add_sub_cancel_right, Complex.norm_cpow_real] using h
  have hpow : ‖w‖ ^ q = ‖w‖ * ‖w‖ ^ (q - 1) := by
    calc
      ‖w‖ ^ q = ‖w‖ ^ ((q - 1) + 1) := by congr 1; ring
      _ = ‖w‖ ^ (q - 1) * ‖w‖ ^ (1 : ℝ) := Real.rpow_add hwpos _ _
      _ = ‖w‖ * ‖w‖ ^ (q - 1) := by rw [Real.rpow_one]; ring
  have hfirst : epsilon c q ‖w‖ ≤ ‖w‖ ^ (q - 1) - 2 := by
    unfold epsilon
    exact min_le_left _ _
  have hsecond : epsilon c q ‖w‖ ≤ 1 - ‖c‖ / ‖w‖ := by
    unfold epsilon
    exact min_le_right _ _
  have hc' : ‖c‖ ≤ ‖w‖ * (1 - epsilon c q ‖w‖) := by
    have hdiv : ‖c‖ / ‖w‖ ≤ 1 - epsilon c q ‖w‖ := by linarith
    have h := (div_le_iff₀ hwpos).mp hdiv
    nlinarith
  have hpow' : ‖w‖ * (2 + epsilon c q ‖w‖) ≤ ‖w‖ ^ q := by
    rw [hpow]
    have hmul := mul_le_mul_of_nonneg_left
      (show 2 + epsilon c q ‖w‖ ≤ ‖w‖ ^ (q - 1) by linarith)
      (le_of_lt hwpos)
    nlinarith
  have hgrowth : ‖w‖ + 2 * ‖w‖ * epsilon c q ‖w‖ ≤
      ‖w ^ (q : ℂ) + c‖ := by
    nlinarith [htriangle, hpow', hc']
  have hmargin : radius q * epsilon c q ‖w‖ <
      ‖w‖ * epsilon c q ‖w‖ :=
    mul_lt_mul_of_pos_right hr hepos
  nlinarith [hgrowth, hmargin]

/-- Falk's linear-growth induction: the same positive k works at every step. -/
theorem linear_growth (c : ℂ) (q : ℝ) (hq : 1 < q) (z : ℂ)
    (hz : max (radius q) ‖c‖ < ‖z‖) :
    ∃ k : ℝ, 0 < k ∧ ∀ n : ℕ,
      ‖orbit c q z n‖ ≥ ‖z‖ + (n : ℝ) * k := by
  have hzr : radius q < ‖z‖ := (max_lt_iff.mp hz).1
  have hzc : ‖c‖ < ‖z‖ := (max_lt_iff.mp hz).2
  have hzpos : 0 < ‖z‖ := (radius_pos q).trans hzr
  have hzp : 2 < ‖z‖ ^ (q - 1) := pow_gt_two q hq hzr
  let k : ℝ := 2 * radius q * epsilon c q ‖z‖
  have hk : 0 < k := by
    dsimp [k]
    exact mul_pos (mul_pos (by norm_num) (radius_pos q))
      (epsilon_pos c q ‖z‖ hzpos hzp hzc)
  refine ⟨k, hk, ?_⟩
  intro n
  induction n with
  | zero =>
      simp [orbit]
  | succ n ih =>
      have hkn : 0 ≤ (n : ℝ) * k :=
        mul_nonneg (Nat.cast_nonneg _) hk.le
      have hzn : ‖z‖ ≤ ‖orbit c q z n‖ := by linarith
      have hnr : radius q < ‖orbit c q z n‖ := lt_of_lt_of_le hzr hzn
      have hnc : ‖c‖ < ‖orbit c q z n‖ := lt_of_lt_of_le hzc hzn
      have heps : epsilon c q ‖z‖ ≤ epsilon c q ‖orbit c q z n‖ :=
        epsilon_mono c q hq hzpos hzn
      have hfact : 0 ≤ 2 * radius q :=
        (mul_pos (by norm_num : (0 : ℝ) < 2) (radius_pos q)).le
      have hle : k ≤ 2 * radius q * epsilon c q ‖orbit c q z n‖ := by
        dsimp [k]
        exact mul_le_mul_of_nonneg_left heps hfact
      have hstep : ‖orbit c q z n‖ + k < ‖orbit c q z (n + 1)‖ := by
        have h := one_step c q hq (orbit c q z n) hnr hnc
        change ‖orbit c q z n‖ + k <
          ‖(orbit c q z n) ^ (q : ℂ) + c‖
        linarith
      simp only [Nat.cast_add, Nat.cast_one]
      nlinarith [ih, hstep]

/-- The Mandelbrot/Julia escape criterion for every real exponent q > 1. -/
theorem escape_to_infinity (c : ℂ) (q : ℝ) (hq : 1 < q) (z : ℂ)
    (hz : max (radius q) ‖c‖ < ‖z‖) :
    Tendsto (fun n : ℕ => ‖orbit c q z n‖) atTop atTop := by
  obtain ⟨k, hk, hbound⟩ := linear_growth c q hq z hz
  apply Filter.tendsto_atTop.2
  intro b
  obtain ⟨N, hN⟩ := exists_nat_ge ((b - ‖z‖) / k)
  filter_upwards [Filter.eventually_ge_atTop N] with n hn
  have hcast : (N : ℝ) ≤ (n : ℝ) := by exact_mod_cast hn
  have hb : b ≤ ‖z‖ + (n : ℝ) * k := by
    have hdiv : (b - ‖z‖) / k ≤ (n : ℝ) := le_trans hN hcast
    have hmul := (div_le_iff₀ hk).mp hdiv
    nlinarith
  exact le_trans hb (hbound n)

end MandelbrotEscape

#print axioms MandelbrotEscape.escape_to_infinity
