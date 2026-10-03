package com.example.data.repository

import com.example.data.model.ChemistryNote
import com.example.data.model.NoteQuizQuestion

object NotesData {
    val notes: List<ChemistryNote> = listOf(
        ChemistryNote(
            id = "note_acids_bases",
            title = "Acids, Bases & Salts",
            category = "Inorganic Chemistry",
            summary = "Fundamental concepts of Arrhenius, Brønsted-Lowry, and Lewis theories of acids and bases, pH scale, and neutralization.",
            keyPoints = listOf(
                "Acids produce H⁺ (H₃O⁺ hydronium) ions in aqueous solution; sour taste; pH < 7.",
                "Bases produce OH⁻ (hydroxide) ions in aqueous solution; bitter taste; slippery touch; pH > 7.",
                "Strong acids/bases dissociate 100% (e.g., HCl, HNO₃, NaOH, KOH).",
                "Weak acids/bases dissociate only partially (e.g., CH₃COOH, NH₃).",
                "pH = -log₁₀[H⁺], pOH = -log₁₀[OH⁻], and pH + pOH = 14 at 25°C."
            ),
            importantFormulas = listOf(
                "pH = -log₁₀[H⁺]",
                "pOH = -log₁₀[OH⁻]",
                "Kw = [H⁺][OH⁻] = 1.0 × 10⁻¹⁴ (at 25°C)",
                "pH + pOH = 14"
            ),
            chemicalEquations = listOf(
                "HCl(aq) + NaOH(aq) → NaCl(aq) + H₂O(l) + Heat",
                "CH₃COOH(aq) + H₂O(l) ⇌ CH₃COO⁻(aq) + H₃O⁺(aq)",
                "2HCl(aq) + CaCO₃(s) → CaCl₂(aq) + H₂O(l) + CO₂(g)↑"
            ),
            examTips = listOf(
                "Remember: Always add acid to water slowly, NEVER water to acid (exothermic heat causes boiling & spattering)!",
                "At equivalence point of strong acid + strong base, pH is exactly 7.0.",
                "Phenolphthalein is COLORLESS in acid and BRIGHT PINK in alkali (pH > 8.2)."
            ),
            simpleExplanation = "Think of acids as proton givers and bases as proton catchers. When they meet, they cancel each other out into harmless salt water!",
            realWorldExample = "Antacid tablets (like magnesium hydroxide or calcium carbonate) neutralize excess stomach hydrochloric acid when you have heartburn.",
            quickQuiz = NoteQuizQuestion(
                question = "What is the pH of a 0.01 M HCl solution?",
                options = listOf("pH = 1", "pH = 2", "pH = 7", "pH = 12"),
                correctIndex = 1,
                explanation = "0.01 M is 10⁻² M. Since pH = -log[H⁺] = -log(10⁻²) = 2."
            )
        ),

        ChemistryNote(
            id = "note_bonding",
            title = "Chemical Bonding & Molecular Shapes",
            category = "Physical Chemistry",
            summary = "Ionic, covalent, coordinate, and metallic bonds, octet rule, electronegativity differences, and VSEPR theory geometries.",
            keyPoints = listOf(
                "Ionic bonds form by complete transfer of electrons between metals and non-metals (electronegativity diff > 1.7).",
                "Covalent bonds form by mutual sharing of electron pairs between non-metals.",
                "Polar covalent bonds have unequal sharing due to electronegativity differences (e.g. H₂O, HCl).",
                "VSEPR Theory: Electron pairs repel each other to maximize spatial separation.",
                "Linear (180°), Trigonal Planar (120°), Tetrahedral (109.5°), Pyramidal (107° for NH₃), Bent (104.5° for H₂O)."
            ),
            importantFormulas = listOf(
                "Formal Charge = Valence e⁻ - Nonbonding e⁻ - ½(Bonding e⁻)",
                "Bond Order = ½(Bonding e⁻ - Antibonding e⁻)"
            ),
            chemicalEquations = listOf(
                "Na(s) + ½Cl₂(g) → Na⁺Cl⁻(s) [ΔH = -411 kJ/mol]",
                "H₂(g) + Cl₂(g) → 2HCl(g)"
            ),
            examTips = listOf(
                "Water (H₂O) has a bent shape (104.5°) because the 2 lone pairs on oxygen repel bonding pairs more strongly than bonding pairs repel each other.",
                "Diamond has 3D giant tetrahedral covalent network (hardest), while Graphite has 2D hexagonal layers with free delocalized electrons (conducts electricity)."
            ),
            simpleExplanation = "Atoms bond to achieve stable outer electron shells like noble gases, either by stealing electrons (ionic) or sharing them like teammates (covalent).",
            realWorldExample = "Table salt crystals are held by super strong ionic bonds that require over 800°C to melt, whereas water ice melts at 0°C because intermolecular hydrogen bonds are much gentler.",
            quickQuiz = NoteQuizQuestion(
                question = "Which molecule possesses a linear molecular geometry?",
                options = listOf("H₂O", "CH₄", "CO₂", "NH₃"),
                correctIndex = 2,
                explanation = "CO₂ has 2 double bonds and zero lone pairs on the central carbon atom, resulting in a 180° linear geometry."
            )
        ),

        ChemistryNote(
            id = "note_reactions",
            title = "Types of Chemical Reactions",
            category = "Basic Chemistry",
            summary = "Classification of reactions: Combination, Decomposition, Displacement, Double Displacement, Redox, and Neutralization.",
            keyPoints = listOf(
                "Combination: Two or more reactants combine to form a single product (A + B → AB).",
                "Decomposition: A single compound breaks down into two or more simpler substances (AB → A + B).",
                "Single Displacement: A more reactive element displaces a less reactive element from its compound (A + BC → AC + B).",
                "Double Displacement: Mutual exchange of ions between two compounds (AB + CD → AD + CB).",
                "Redox: Oxidation is Loss of electrons (OIL); Reduction is Gain of electrons (RIG)."
            ),
            importantFormulas = listOf(
                "Oxidation Number rules: Pure element = 0; Group 1 = +1; Oxygen = -2 (usually); Hydrogen = +1 (with non-metals)."
            ),
            chemicalEquations = listOf(
                "2Mg + O₂ → 2MgO (Combination)",
                "2KClO₃ → 2KCl + 3O₂↑ (Decomposition)",
                "Zn + CuSO₄ → ZnSO₄ + Cu (Displacement)",
                "AgNO₃ + NaCl → AgCl↓ + NaNO₃ (Double Displacement)"
            ),
            examTips = listOf(
                "Reactivity series order: K > Na > Ca > Mg > Al > Zn > Fe > Pb > [H] > Cu > Hg > Ag > Au.",
                "Copper cannot displace hydrogen from dilute acids because copper is below hydrogen in the reactivity series."
            ),
            simpleExplanation = "Reactions are like social dances: atoms can pair up (combination), break apart (decomposition), or swap partners (displacement).",
            realWorldExample = "Iron rusting is a slow redox reaction between iron, oxygen, and moisture in the air to form hydrated iron(III) oxide.",
            quickQuiz = NoteQuizQuestion(
                question = "In the reaction Zn + Cu²⁺ → Zn²⁺ + Cu, what is being oxidized?",
                options = listOf("Zn", "Cu²⁺", "Cu", "Zn²⁺"),
                correctIndex = 0,
                explanation = "Zinc loses 2 electrons (Zn → Zn²⁺ + 2e⁻), so Zinc undergoes oxidation (OIL: Oxidation Is Loss)."
            )
        ),

        ChemistryNote(
            id = "note_organic",
            title = "Organic Chemistry Fundamentals",
            category = "Organic Chemistry",
            summary = "Hydrocarbons, functional groups, IUPAC nomenclature, isomerism, and essential reaction mechanisms.",
            keyPoints = listOf(
                "Alkanes (CnH2n+2): Saturated single bonds, undergo substitution reactions.",
                "Alkenes (CnH2n): Unsaturated double bonds, undergo electrophilic addition reactions.",
                "Alkynes (CnH2n-2): Triple bonds.",
                "Functional groups: Alcohols (-OH), Aldehydes (-CHO), Ketones (-CO-), Carboxylic Acids (-COOH), Esters (-COO-).",
                "Esterification: Acid + Alcohol in the presence of conc. H₂SO₄ produces sweet-smelling ester + water."
            ),
            importantFormulas = listOf(
                "Alkanes: CₙH₂ₙ₊₂",
                "Alkenes: CₙH₂ₙ",
                "Alkynes: CₙH₂ₙ₋₂",
                "Alcohols: CₙH₂ₙ₊₁OH"
            ),
            chemicalEquations = listOf(
                "CH₄ + Cl₂ → CH₃Cl + HCl (Free radical substitution under UV light)",
                "C₂H₄ + Br₂ → C₂H₄Br₂ (Addition of bromine, brown color discharges)",
                "CH₃COOH + C₂H₅OH ⇌ CH₃COOC₂H₅ + H₂O (Fischer Esterification)"
            ),
            examTips = listOf(
                "Bromine water test: Alkenes decolorize orange bromine water immediately; alkanes do not react in the dark.",
                "Aldehydes give a silver mirror test with Tollen's reagent; ketones do not."
            ),
            simpleExplanation = "Organic chemistry is the chemistry of Carbon's incredible lego-brick ability to form infinite chains, branches, and rings.",
            realWorldExample = "Vanilla flavoring, banana scent (isoamyl acetate), and nail polish remover (acetone) are all everyday functional organic compounds.",
            quickQuiz = NoteQuizQuestion(
                question = "What functional group is present in CH₃COOH?",
                options = listOf("Alcohol", "Aldehyde", "Carboxylic Acid", "Ketone"),
                correctIndex = 2,
                explanation = "The -COOH group is the carboxyl group characteristic of carboxylic acids (ethanoic/acetic acid)."
            )
        )
    )
}
