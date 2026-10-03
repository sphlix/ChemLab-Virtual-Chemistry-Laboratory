package com.example.data.repository

import com.example.data.model.Experiment
import com.example.data.model.ExperimentCategory
import com.example.data.model.ExperimentStep
import com.example.data.model.StepVisualType

object ExperimentsData {
    val allExperiments: List<Experiment> = listOf(
        // BASIC CHEMISTRY
        Experiment(
            id = "exp_acid_base",
            title = "Acid and Base Reaction",
            category = ExperimentCategory.BASIC,
            difficulty = "Beginner",
            estimatedTime = "10 min",
            chemicals = listOf("HCl (0.1M)", "NaOH (0.1M)", "Phenolphthalein Indicator"),
            apparatus = listOf("Test tubes", "Test tube rack", "Dropper", "Thermometer"),
            description = "Explore how hydrochloric acid and sodium hydroxide neutralize each other with an indicator and temperature rise.",
            steps = listOf(
                ExperimentStep(
                    1, "Collect Apparatus", "Place clean test tubes into the rack and ready the dropper.",
                    "Clean glassware avoids ionic contamination that could skew pH observations.",
                    "Test tube, Dropper", "None", "", "Apparatus is clean and positioned securely.", StepVisualType.COLLECT_APPARATUS
                ),
                ExperimentStep(
                    2, "Add Sodium Hydroxide", "Measure 5 mL of 0.1M NaOH solution into the test tube.",
                    "Provides hydroxide ions (OH⁻) creating an alkaline baseline environment.",
                    "Measuring cylinder, Test tube", "NaOH (0.1M)", "", "Clear, colorless alkaline solution in the tube.", StepVisualType.MEASURE_LIQUID
                ),
                ExperimentStep(
                    3, "Add Indicator", "Add 2 drops of phenolphthalein indicator to the tube and swirl.",
                    "Phenolphthalein undergoes a structural resonance rearrangement above pH 8.2.",
                    "Dropper", "Phenolphthalein", "H₂In + 2OH⁻ ⇌ In²⁻ + 2H₂O", "Solution instantly turns a vibrant, glowing fuchsia pink.", StepVisualType.ADD_INDICATOR
                ),
                ExperimentStep(
                    4, "Neutralize with Acid", "Add dilute HCl drop by drop while gently swirling until pink just disappears.",
                    "H⁺ ions from HCl combine with OH⁻ to form neutral H₂O, driving pH below 8.2.",
                    "Dropper, Thermometer", "HCl (0.1M)", "HCl + NaOH → NaCl + H₂O", "Pink color flashes and fades away; temperature rises by ~4°C.", StepVisualType.POUR_MIX
                )
            )
        ),

        Experiment(
            id = "exp_neutralization",
            title = "Neutralization Enthalpy",
            category = ExperimentCategory.BASIC,
            difficulty = "Beginner",
            estimatedTime = "12 min",
            chemicals = listOf("HCl (1.0M)", "NaOH (1.0M)", "Distilled Water"),
            apparatus = listOf("Insulated calorimeter beaker", "Precision Thermometer", "Measuring cylinder", "Stirrer"),
            description = "Measure the exothermic heat evolved when an acid completely neutralizes a strong alkali.",
            steps = listOf(
                ExperimentStep(
                    1, "Measure Initial Temperatures", "Pour 25 mL of HCl and 25 mL of NaOH into separate containers and record initial temperatures (25.0°C).",
                    "Ensures thermal equilibrium before starting the reaction.",
                    "Measuring cylinders, Thermometer", "HCl, NaOH", "", "Both solutions steady at 25.0°C.", StepVisualType.MEASURE_LIQUID
                ),
                ExperimentStep(
                    2, "Combine Solutions", "Quickly pour NaOH into the HCl inside the insulated calorimeter beaker and insert thermometer.",
                    "Prevents heat exchange with the surrounding atmosphere.",
                    "Calorimeter, Stirrer", "HCl + NaOH", "H⁺(aq) + OH⁻(aq) → H₂O(l) + 57.1 kJ/mol", "Solution mixes rapidly without gas bubbles.", StepVisualType.POUR_MIX
                ),
                ExperimentStep(
                    3, "Record Peak Temperature", "Stir gently and observe the thermometer rise to 31.8°C.",
                    "Bond formation between H⁺ and OH⁻ releases exothermic neutralization enthalpy.",
                    "Thermometer", "Mixture", "ΔT = 6.8°C", "Temperature peaks at 31.8°C, confirming exothermic energy release.", StepVisualType.HEAT_BUNSEN
                )
            )
        ),

        Experiment(
            id = "exp_indicators",
            title = "Indicators & pH Scale",
            category = ExperimentCategory.BASIC,
            difficulty = "Beginner",
            estimatedTime = "15 min",
            chemicals = listOf("HCl", "NaOH", "CH3COOH", "Phenolphthalein", "Methyl Orange", "Universal Indicator"),
            apparatus = listOf("Spotting plate", "Pipette", "pH color chart"),
            description = "Test various household and lab solutions against multiple chemical indicators to observe full-spectrum color shifts.",
            steps = listOf(
                ExperimentStep(
                    1, "Prepare Spotting Plate", "Add 3 drops of acid and alkali solutions into separate wells on the spotting plate.",
                    "Microscale testing saves reagents and provides side-by-side color comparison.",
                    "Spotting plate, Pipette", "HCl, NaOH, CH3COOH", "", "Liquids neatly positioned in porcelain wells.", StepVisualType.COLLECT_APPARATUS
                ),
                ExperimentStep(
                    2, "Add Universal Indicator", "Add 1 drop of Universal Indicator to each well.",
                    "Universal indicator contains thymol blue, methyl red, bromothymol blue, and phenolphthalein.",
                    "Dropper", "Universal Indicator", "", "Acid well turns fiery red (pH 1-2); base well turns deep royal purple (pH 13).", StepVisualType.ADD_INDICATOR
                ),
                ExperimentStep(
                    3, "Compare Methyl Orange", "Test HCl and NaOH with Methyl Orange indicator.",
                    "Methyl orange changes color over pH range 3.1 (red) to 4.4 (yellow).",
                    "Dropper", "Methyl Orange", "", "Acid turns red; alkaline turns bright golden yellow.", StepVisualType.ADD_INDICATOR
                )
            )
        ),

        Experiment(
            id = "exp_solubility",
            title = "Solubility & Saturation",
            category = ExperimentCategory.BASIC,
            difficulty = "Beginner",
            estimatedTime = "15 min",
            chemicals = listOf("NaCl", "CuSO4", "Distilled Water"),
            apparatus = listOf("Beaker (100 mL)", "Glass stirring rod", "Spatula", "Bunsen burner", "Tripod"),
            description = "Investigate solute dissolution limits, saturation curves, and temperature dependence of solubility.",
            steps = listOf(
                ExperimentStep(
                    1, "Dissolve at Room Temp", "Add spatulas of Copper Sulfate into 50 mL water, stirring until crystals no longer dissolve.",
                    "Dynamic equilibrium is reached where rate of dissolution equals rate of precipitation.",
                    "Beaker, Glass rod", "CuSO4, Water", "CuSO₄(s) ⇌ Cu²⁺(aq) + SO₄²⁻(aq)", "Deep blue solution forms with undissolved blue crystals on the bottom.", StepVisualType.POUR_MIX
                ),
                ExperimentStep(
                    2, "Apply Heat", "Place the beaker onto the tripod stand and heat gently with the Bunsen burner.",
                    "Dissolution of most solid salts is endothermic; Le Chatelier's principle drives more solute to dissolve.",
                    "Bunsen burner, Tripod", "CuSO4 solution", "", "Remaining blue crystals completely dissolve, creating a supersaturated solution.", StepVisualType.HEAT_BUNSEN
                )
            )
        ),

        Experiment(
            id = "exp_filtration",
            title = "Filtration & Separation",
            category = ExperimentCategory.BASIC,
            difficulty = "Beginner",
            estimatedTime = "12 min",
            chemicals = listOf("Chalk powder (CaCO3)", "Sand", "Water"),
            apparatus = listOf("Filter funnel", "Filter paper cone", "Conical flask", "Beaker"),
            description = "Separate insoluble precipitates and heterogeneous mixtures using gravity filtration.",
            steps = listOf(
                ExperimentStep(
                    1, "Fold Filter Paper", "Fold circular filter paper into quarters and open into a cone inside the glass funnel.",
                    "Proper seating prevents unfiltered slurry from bypassing the paper edges.",
                    "Filter paper, Glass funnel", "None", "", "Cone fits snugly inside funnel; moistened with droplets of distilled water.", StepVisualType.COLLECT_APPARATUS
                ),
                ExperimentStep(
                    2, "Pour Suspension", "Pour the turbid chalk suspension slowly along a glass rod into the center of the cone.",
                    "Pore size of filter paper traps solid particles while letting water molecules pass.",
                    "Glass rod, Conical flask", "CaCO3 suspension", "", "Clear transparent filtrate collects in the conical flask; residue remains on paper.", StepVisualType.FILTER_PRECIPITATE
                )
            )
        ),

        Experiment(
            id = "exp_crystallization",
            title = "Crystallization of Copper Sulfate",
            category = ExperimentCategory.BASIC,
            difficulty = "Intermediate",
            estimatedTime = "20 min",
            chemicals = listOf("CuSO4 pentahydrate", "Dilute H2SO4", "Distilled Water"),
            apparatus = listOf("Evaporating dish", "Bunsen burner", "Tripod & Gauze", "Watch glass"),
            description = "Grow beautiful geometric blue rhombic crystals of copper(II) sulfate pentahydrate from saturated solution.",
            steps = listOf(
                ExperimentStep(
                    1, "Prepare Saturated Hot Solution", "Dissolve excess CuSO4 in 40 mL warm water acidified with 2 drops of H2SO4.",
                    "Acid prevents hydrolysis of copper ions into insoluble basic copper salts.",
                    "Beaker, Bunsen burner", "CuSO4, H2SO4", "", "Rich sapphire blue hot solution formed.", StepVisualType.HEAT_BUNSEN
                ),
                ExperimentStep(
                    2, "Evaporate to Crystallization Point", "Heat gently until a drop on a cold glass rod forms tiny crusts.",
                    "Signals that the solution is supersaturated at room temperature.",
                    "Evaporating dish, Glass rod", "Hot CuSO4 solution", "", "Solution concentrates; crust appears on rim.", StepVisualType.HEAT_BUNSEN
                ),
                ExperimentStep(
                    3, "Cool and Crystallize", "Remove heat and cover loosely with a watch glass; allow undisturbed cooling.",
                    "Slow cooling allows copper and sulfate ions to orient into ordered triclinic crystal lattices.",
                    "Watch glass", "CuSO4.5H2O", "Cu²⁺ + SO₄²⁻ + 5H₂O → CuSO₄·5H₂O(s)", "Stunning translucent deep-blue rhombic crystals grow at the bottom.", StepVisualType.FILTER_PRECIPITATE
                )
            )
        ),

        // INORGANIC CHEMISTRY
        Experiment(
            id = "exp_flame_tests",
            title = "Flame Tests for Metal Ions",
            category = ExperimentCategory.INORGANIC,
            difficulty = "Intermediate",
            estimatedTime = "15 min",
            chemicals = listOf("NaCl (Sodium)", "KCl (Potassium)", "CuSO4 (Copper)", "CaCl2 (Calcium)", "BaCl2 (Barium)", "Concentrated HCl"),
            apparatus = listOf("Nichrome / Platinum loop wire", "Bunsen burner", "Cobalt blue glass", "Watch glass"),
            description = "Identify metallic cations through characteristic atomic emission spectrum colors in a non-luminous Bunsen flame.",
            steps = listOf(
                ExperimentStep(
                    1, "Clean the Wire Loop", "Dip nichrome wire in conc. HCl and hold in the hottest non-luminous blue flame until colorless.",
                    "Converts metallic impurities into volatile metal chlorides that burn away completely.",
                    "Nichrome loop, Bunsen burner", "Conc. HCl", "", "Flame stays clean blue with no stray yellow contamination.", StepVisualType.HEAT_BUNSEN
                ),
                ExperimentStep(
                    2, "Test Sodium (Na⁺)", "Dip loop into paste of NaCl and insert into flame edge.",
                    "Thermally excited 3p valence electrons drop to 3s ground state, emitting 589 nm yellow photons.",
                    "Loop, Flame", "NaCl", "Na*(excited) → Na + hν (589nm)", "Intense brilliant golden yellow flame flares up!", StepVisualType.OBSERVE_FLAME
                ),
                ExperimentStep(
                    3, "Test Potassium (K⁺)", "Clean loop and test KCl, viewing through cobalt glass.",
                    "Cobalt glass absorbs yellow sodium contamination, revealing delicate 766 nm lilac/violet radiation.",
                    "Loop, Cobalt glass", "KCl", "K*(excited) → K + hν (766nm)", "Gentle lilac / violet flame observed.", StepVisualType.OBSERVE_FLAME
                ),
                ExperimentStep(
                    4, "Test Copper (Cu²⁺)", "Test Copper(II) salt in flame.",
                    "Electronic transitions in copper atomic orbitals emit characteristic green wavelengths.",
                    "Loop, Flame", "CuSO4", "", "Magnificent emerald-green / bluish-green flame burns bright.", StepVisualType.OBSERVE_FLAME
                ),
                ExperimentStep(
                    5, "Test Calcium (Ca²⁺)", "Test Calcium salt in flame.",
                    "Calcium emissions peak in the red-orange spectral band.",
                    "Loop, Flame", "CaCl2", "", "Characteristic brick-red / orange-red flame flashes.", StepVisualType.OBSERVE_FLAME
                )
            )
        ),

        Experiment(
            id = "exp_precipitation",
            title = "Precipitation & Qualitative Analysis",
            category = ExperimentCategory.INORGANIC,
            difficulty = "Intermediate",
            estimatedTime = "18 min",
            chemicals = listOf("AgNO3", "NaCl", "BaCl2", "H2SO4", "Pb(NO3)2", "KI"),
            apparatus = listOf("Test tubes", "Test tube rack", "Droppers"),
            description = "Identify unknown halides and sulfates through distinctive insoluble precipitate formations.",
            steps = listOf(
                ExperimentStep(
                    1, "Chloride Test with AgNO3", "Add 5 drops of AgNO3 solution to 3 mL of NaCl solution.",
                    "Ag⁺ binds Cl⁻ into insoluble Silver Chloride solid.",
                    "Test tube, Dropper", "AgNO3 + NaCl", "Ag⁺ + Cl⁻ → AgCl↓ (white)", "Curdy white precipitate forms instantly (soluble in NH4OH).", StepVisualType.POUR_MIX
                ),
                ExperimentStep(
                    2, "Sulfate Test with BaCl2", "Add 5 drops of BaCl2 solution to 3 mL of dilute H2SO4.",
                    "Barium sulfate has an exceptionally low solubility product and resists acid dissolution.",
                    "Test tube, Dropper", "BaCl2 + H2SO4", "Ba²⁺ + SO₄²⁻ → BaSO₄↓ (white)", "Dense chalky white precipitate forms.", StepVisualType.POUR_MIX
                ),
                ExperimentStep(
                    3, "Iodide Test: Golden Rain", "Add potassium iodide (KI) to lead nitrate Pb(NO3)2.",
                    "Formation of lead(II) iodide has a signature bright yellow crystal stoichiometry.",
                    "Test tube, Dropper", "Pb(NO3)2 + KI", "Pb²⁺ + 2I⁻ → PbI₂↓ (yellow)", "Vibrant canary-yellow precipitate forms instantly!", StepVisualType.POUR_MIX
                )
            )
        ),

        Experiment(
            id = "exp_gas_id",
            title = "Identification of Gases",
            category = ExperimentCategory.INORGANIC,
            difficulty = "Intermediate",
            estimatedTime = "15 min",
            chemicals = listOf("Zn granules", "Dilute HCl", "Na2CO3", "Limewater Ca(OH)2", "Wooden splints"),
            apparatus = listOf("Delivery tube with stopper", "Boiling tube", "Test tubes", "Matches"),
            description = "Generate and test Carbon Dioxide (CO₂), Hydrogen (H₂), and Oxygen (O₂) using signature confirmatory tests.",
            steps = listOf(
                ExperimentStep(
                    1, "Hydrogen Gas Test (Pop Test)", "Add Zn to dilute HCl in a boiling tube and trap escaping gas. Bring a burning splint near mouth.",
                    "Hydrogen mixes with atmospheric oxygen and ignites explosively in stoichiometric ratio.",
                    "Boiling tube, Burning splint", "Zn + HCl", "2H₂ + O₂ → 2H₂O + Energy", "Splint extinguishes with a distinct, sharp 'POP' explosion sound!", StepVisualType.GAS_COLLECT
                ),
                ExperimentStep(
                    2, "Carbon Dioxide Test (Limewater)", "React Na2CO3 with HCl and bubble the evolving gas through clear limewater.",
                    "CO₂ reacts with Ca(OH)2 to precipitate insoluble calcium carbonate.",
                    "Delivery tube, Test tube", "Na2CO3 + HCl, Ca(OH)2", "CO₂ + Ca(OH)₂ → CaCO₃↓ + H₂O", "Limewater turns visibly cloudy and milky white.", StepVisualType.GAS_COLLECT
                )
            )
        ),

        // ORGANIC CHEMISTRY
        Experiment(
            id = "exp_esterification",
            title = "Esterification (Sweet Ester Synthesis)",
            category = ExperimentCategory.ORGANIC,
            difficulty = "Intermediate",
            estimatedTime = "20 min",
            chemicals = listOf("Glacial Acetic Acid (CH3COOH)", "Ethanol (C2H5OH)", "Conc. H2SO4", "Water"),
            apparatus = listOf("Boiling tube", "Water bath beaker", "Bunsen burner", "Beaker with cold water"),
            description = "Synthesize Ethyl Acetate ester with its pleasant fruity pear aroma through Fischer esterification.",
            steps = listOf(
                ExperimentStep(
                    1, "Mix Organic Reagents", "In a dry boiling tube, add 3 mL of ethanol and 3 mL of glacial acetic acid.",
                    "Provides the nucleophilic alcohol and electrophilic carbonyl carboxylic acid.",
                    "Boiling tube, Pipette", "C2H5OH, CH3COOH", "", "Colorless liquid mixture.", StepVisualType.MEASURE_LIQUID
                ),
                ExperimentStep(
                    2, "Add Acid Catalyst", "Carefully add 4 drops of concentrated sulfuric acid down the inner wall of the tube.",
                    "H2SO4 protonates the carbonyl oxygen (increasing electrophilicity) and acts as dehydrating agent to shift equilibrium.",
                    "Dropper", "Conc. H2SO4", "", "Mixture warms slightly; remains clear.", StepVisualType.POUR_MIX
                ),
                ExperimentStep(
                    3, "Warm in Water Bath", "Heat boiling tube inside a 60-70°C water bath for 10 minutes. (Never heat alcohol directly over open flame!)",
                    "Ethanol is flammable; indirect water bath maintains controlled reflux temperature.",
                    "Water bath, Thermometer", "Heat", "CH₃COOH + C₂H₅OH ⇌ CH₃COOC₂H₅ + H₂O", "Vapors condense gently along tube neck.", StepVisualType.HEAT_BUNSEN
                ),
                ExperimentStep(
                    4, "Quench and Odor Detection", "Pour contents into a beaker of cold water containing a pinch of NaHCO3.",
                    "Water dissolves unreacted acid and alcohol; ester forms immiscible fruity layer on top.",
                    "Beaker", "Cold water + NaHCO3", "", "Immiscible layer floats on surface with a distinct sweet, fruity banana/apple fragrance!", StepVisualType.POUR_MIX
                )
            )
        ),

        Experiment(
            id = "exp_saponification",
            title = "Saponification (Soap Making)",
            category = ExperimentCategory.ORGANIC,
            difficulty = "Intermediate",
            estimatedTime = "25 min",
            chemicals = listOf("Vegetable oil (Triglyceride)", "NaOH solution (20%)", "NaCl (common salt)", "Water"),
            apparatus = listOf("Beaker (250 mL)", "Glass rod", "Bunsen burner & Tripod", "Molds"),
            description = "Hydrolyze vegetable oil triglycerides with caustic alkali to produce soap and glycerol.",
            steps = listOf(
                ExperimentStep(
                    1, "Mix Oil and Alkali", "Place 20 mL vegetable oil and 30 mL 20% NaOH solution into beaker.",
                    "Strong base attacks ester bonds of fatty acid triglycerides.",
                    "Beaker, Glass rod", "Oil + NaOH", "Triglyceride + 3NaOH → Glycerol + 3 Soap molecules", "Cloudy emulsion forms as heating begins.", StepVisualType.POUR_MIX
                ),
                ExperimentStep(
                    2, "Heat and Stir", "Heat with gentle boiling for 15 minutes, stirring continuously until pasty mass forms.",
                    "Thermal kinetic energy accelerates alkaline ester cleavage.",
                    "Bunsen burner, Tripod", "Heat", "", "Thick creamy soap paste forms in beaker.", StepVisualType.HEAT_BUNSEN
                ),
                ExperimentStep(
                    3, "Salting Out with NaCl", "Add saturated NaCl brine solution and stir vigorously.",
                    "Increases ionic strength of medium, drastically reducing soap solubility and causing curd to separate.",
                    "Beaker, Spatula", "NaCl (brine)", "Salting out soap curd", "Solid curd of soap floats to top; clear glycerol remains below.", StepVisualType.FILTER_PRECIPITATE
                )
            )
        ),

        // PHYSICAL CHEMISTRY
        Experiment(
            id = "exp_titration",
            title = "Acid-Base Titration",
            category = ExperimentCategory.PHYSICAL,
            difficulty = "Advanced",
            estimatedTime = "25 min",
            chemicals = listOf("HCl (standard solution)", "NaOH (unknown concentration)", "Phenolphthalein indicator"),
            apparatus = listOf("Burette (50 mL) & Stand", "Pipette (20 mL)", "Conical flask (250 mL)", "White tile", "Funnel"),
            description = "Determine the exact molar concentration of unknown alkali by quantitative neutralization with standard acid.",
            steps = listOf(
                ExperimentStep(
                    1, "Prepare Burette", "Rinse burette with standard 0.10M HCl, fill to 0.00 mL mark using funnel, remove air bubble from tip.",
                    "Air bubbles in the nozzle displace liquid, causing erroneous titer readings.",
                    "Burette, Stand, Funnel", "HCl (0.10M)", "", "HCl meniscus reads exactly 0.00 mL.", StepVisualType.TITRATE
                ),
                ExperimentStep(
                    2, "Pipette Base into Flask", "Pipette exactly 20.0 mL of unknown NaOH into conical flask; place on white tile.",
                    "White tile provides maximum contrast for detecting the subtle endpoint color fade.",
                    "Pipette, Conical flask, White tile", "NaOH", "", "20.0 mL clear liquid in flask.", StepVisualType.MEASURE_LIQUID
                ),
                ExperimentStep(
                    3, "Add Indicator", "Add 2-3 drops of phenolphthalein to the conical flask.",
                    "Indicates equivalence point where moles of H⁺ equal moles of OH⁻.",
                    "Dropper", "Phenolphthalein", "", "Solution turns intense fuchsia pink.", StepVisualType.ADD_INDICATOR
                ),
                ExperimentStep(
                    4, "Perform Titration", "Run HCl from burette while continuously swirling flask. Near endpoint, add dropwise.",
                    "Ensures homogenous distribution and prevents overshooting the equivalence point.",
                    "Burette, Conical flask", "HCl + NaOH", "HCl + NaOH → NaCl + H₂O", "One single drop causes the pink color to permanently discharge to colorless!", StepVisualType.TITRATE
                ),
                ExperimentStep(
                    5, "Calculate Molarity", "Record final burette reading (e.g. 19.8 mL). Use M₁V₁ = M₂V₂ to compute NaOH molarity.",
                    "Stoichiometric ratio of 1:1 enables calculation of unknown concentration.",
                    "Calculator", "M₁V₁ = M₂V₂", "M(NaOH) = (0.10 × 19.8) / 20.0 = 0.099 M", "Concentration determined: 0.099 M (99.0% accuracy).", StepVisualType.TITRATE
                )
            )
        ),

        Experiment(
            id = "exp_reaction_rate",
            title = "Rate of Chemical Reaction",
            category = ExperimentCategory.PHYSICAL,
            difficulty = "Intermediate",
            estimatedTime = "20 min",
            chemicals = listOf("Sodium Thiosulfate (Na2S2O3)", "Dilute HCl", "Distilled Water"),
            apparatus = listOf("Conical flask", "Paper with cross mark 'X'", "Stopwatch", "Thermometer"),
            description = "Investigate how reactant concentration and temperature influence the precipitation rate of colloidal sulfur.",
            steps = listOf(
                ExperimentStep(
                    1, "Position over Cross Mark", "Draw a bold black cross 'X' on white paper and place flask over it.",
                    "Provides a standard visual extinction benchmark for timing turbidity.",
                    "Paper with 'X', Flask", "None", "", "Black cross clearly visible through empty flask bottom.", StepVisualType.COLLECT_APPARATUS
                ),
                ExperimentStep(
                    2, "Initiate Reaction", "Add 50 mL thiosulfate solution, add 5 mL HCl, and immediately start stopwatch.",
                    "Thiosulfate decomposes in acid to generate fine colloidal sulfur particles.",
                    "Stopwatch, Flask", "Na2S2O3 + HCl", "Na₂S₂O₃ + 2HCl → 2NaCl + SO₂ + S↓ + H₂O", "Colloidal yellow sulfur particles begin scattering light.", StepVisualType.POUR_MIX
                ),
                ExperimentStep(
                    3, "Observe Disappearance", "Look down from above until the black cross is completely obscured by sulfur cloud.",
                    "Rate is inversely proportional to time: Rate ∝ 1/time.",
                    "Stopwatch", "Colloidal S", "", "Cross disappears at 34 seconds. Doubling concentration halves the time!", StepVisualType.FILTER_PRECIPITATE
                )
            )
        )
    )

    fun getExperimentById(id: String): Experiment? = allExperiments.find { it.id == id }
}
