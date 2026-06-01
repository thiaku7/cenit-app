package com.example.data

object CenitSubjects {
    val items: List<SubjectProgress> = listOf(
        // ======= PHASE 1 =======
        SubjectProgress(
            id = "01-01",
            phase = 1,
            category = "Mental",
            name = "Psicología del Autocontrol",
            description = "La disciplina no es motivación — es un sistema neurológico que se puede construir. Aprenderás a regular impulsos, diferir gratificación y actuar bajo presión.",
            topics = "Neurociencia del autocontrol y el córtex prefrontal;El modelo del ego depletion y cómo evitarlo;Técnicas de regulación emocional avanzada;Construcción de hábitos con rutinas de anclaje;Manejo del estrés crónico y el cortisol;Mindfulness aplicado (no espiritual, sino clínico)",
            durationText = "3 semanas intensivas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "01-02",
            phase = 1,
            category = "Mental",
            name = "Filosofía Estoica Aplicada",
            description = "Marco de pensamiento más eficiente para la acción bajo incertidumbre. No es teoría — es un protocolo operativo de vida.",
            topics = "Dicotomía del control (Epicteto) aplicada al día a día;Meditación negativa y visualización del peor escenario;Amor fati: convertir obstáculos en combustible;Marco de Marco Aurelio para la toma de decisiones;Diferencia entre reaccionar y responder",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "01-03",
            phase = 1,
            category = "Mental",
            name = "Modelos Mentales y Pensamiento Crítico",
            description = "Los mejores tomadores de decisiones del mundo no tienen más información — tienen mejores marcos de análisis. Aquí construyes los tuyos.",
            topics = "Los 50 modelos mentales esenciales (Munger, Feynman, etc.);Pensamiento de primer principio (Elon Musk / Aristóteles);Sesgos cognitivos: los 25 más peligrosos y cómo evitarlos;Razonamiento bayesiano básico;Mapas vs territorio: no confundir modelos con realidad;Inversión: pensar hacia atrás para evitar errores",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "01-04",
            phase = 1,
            category = "Mental",
            name = "Psicología del Alto Rendimiento",
            description = "Estudio de la mente de personas que operan en los límites humanos: atletas de élite, CEOs, militares especiales. Qué comparten y cómo replicarlo.",
            topics = "Estado de flujo (Flow de Csikszentmihalyi) — cómo inducirlo;Mentalidad de crecimiento vs. fija (Dweck);Identidad y comportamiento: el rol del \"yo\" en el rendimiento;Visualización de resultados con especificidad sensorial;Gestión del miedo al fracaso y la imposter syndrome;Tolerancia a la incomodidad como habilidad entrenable",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "01-05",
            phase = 1,
            category = "Mental",
            name = "Epistemología Práctica",
            description = "Cómo sabes lo que sabes — y cómo distinguir conocimiento real de ilusión de conocimiento. La base intelectual de alguien que no puede ser engañado fácilmente.",
            topics = "Fundamentos de epistemología (Descartes a Popper);Falsabilidad como criterio de validez;El problema de la inducción y cómo manejarlo;Fuentes de información: jerarquía de confiabilidad;Cómo leer estudios científicos sin ser engañado",
            durationText = "2 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "01-06",
            phase = 1,
            category = "Mental",
            name = "Neuroplasticidad y Aprendizaje Acelerado",
            description = "Cómo funciona el cerebro cuando aprende, y cómo diseñar tu propio sistema de aprendizaje para absorber cualquier disciplina en un tercio del tiempo normal.",
            topics = "Método Feynman para dominar conceptos complejos;Espaciado de repeticiones (spaced repetition) con Anki;Recuperación activa vs. lectura pasiva;Sueño, ejercicio y cognición — el trío inseparable;Cómo construir estructuras de conocimiento (chunking);Lectura activa: anotación, síntesis y aplicación",
            durationText = "3 semanas",
            status = "PENDING"
        ),

        // ======= PHASE 2 =======
        SubjectProgress(
            id = "02-01",
            phase = 2,
            category = "Sistemas",
            name = "Gestión del Tiempo Estratégica",
            description = "El tiempo es el único recurso que no se regenera. Aprenderás a valorarlo, protegerlo y usarlo con precisión quirúrgica.",
            topics = "Time blocking y calendario como herramienta de estrategia;Ley de Parkinson: por qué expandimos trabajo al tiempo disponible;El principio 80/20 aplicado a decisiones diarias;Deep work vs. shallow work (Cal Newport);Gestión de energía por ciclos ultradianos;Auditoría semanal de tiempo — método personal",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "02-02",
            phase = 2,
            category = "Sistemas",
            name = "Diseño de Hábitos de Alto Impacto",
            description = "No se trata de fuerza de voluntad. Los hábitos son arquitectura de comportamiento — se diseñan, no se imponen.",
            topics = "El loop del hábito (cue-routine-reward) a nivel avanzado;Stacking de hábitos y anclas comportamentales;Diseño del entorno como herramienta de cambio;Sistemas de seguimiento y accountability;Diferencia entre hábitos de identidad y de resultado;Cómo romper hábitos negativos con sustitución",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "02-03",
            phase = 2,
            category = "Sistemas",
            name = "PKM — Gestión del Conocimiento Personal",
            description = "Tu segundo cerebro. Un sistema externo de almacenamiento, conexión y recuperación de ideas que amplifica tu inteligencia.",
            topics = "Metodología Zettelkasten (notas atómicas y links);Herramientas: Obsidian, Notion — arquitectura práctica;PARA method para organizar proyectos e información;Cómo procesar libros, cursos y videos en conocimiento accionable;Mapas mentales de alto nivel para visión estratégica",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "02-04",
            phase = 2,
            category = "Sistemas",
            name = "Toma de Decisiones Bajo Incertidumbre",
            description = "El mundo real no tiene información perfecta. Esta materia te entrena para decidir bien con datos incompletos, sin paralizarte y sin equivocarte por impulso.",
            topics = "Árbol de decisiones y análisis de consecuencias;Pensamiento probabilístico básico-avanzado;Premortems: imaginar el fracaso antes de actuar;Regret minimization framework (Bezos);Cuándo seguir la intuición vs. el análisis racional;Decisiones reversibles vs. irreversibles — velocidad distinta",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "02-05",
            phase = 2,
            category = "Sistemas",
            name = "Gestión de Proyectos Personal",
            description = "Todo gran logro es un proyecto. Aprenderás a planificar, ejecutar, medir y cerrar proyectos propios con metodología profesional.",
            topics = "GTD (Getting Things Done) — sistema completo;OKRs personales: definir y medir lo que importa;Principio SMART de objetivos — más allá del cliché;Kanban personal y tableros de trabajo;Gestión de la deuda de tareas y procrastinación estructural",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "02-06",
            phase = 2,
            category = "Sistemas",
            name = "Salud Física como Base del Rendimiento",
            description = "El cuerpo es el hardware. Sin él optimizado, la mente opera al 60%. Esto no es fitness — es biohacking con criterio científico.",
            topics = "Entrenamiento de fuerza: protocolo mínimo máximo;Sueño de élite: arquitectura, ritmo circadiano, protocolos;Nutrición para cognición y energía sostenida;Exposición al frío y calor: efectos en el sistema nervioso;Suplementación con evidencia (creatina, magnesio, vitamina D);HRV como indicador de recuperación y estrés",
            durationText = "4 semanas (práctica permanente)",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "02-07",
            phase = 2,
            category = "Sistemas",
            name = "Pensamiento Estratégico a Largo Plazo",
            description = "La diferencia entre personas exitosas y extraordinarias es el horizonte temporal. Aprenderás a pensar en décadas, no en semanas.",
            topics = "Teoría de juegos básica aplicada a decisiones de vida;Diseño de vida: Ikigai, propósito y alineación;Construcción de ventajas compuestas a largo plazo;Visión personal 10 años — métodos de proyección;Estrategia de océano azul aplicada a la carrera personal",
            durationText = "3 semanas",
            status = "PENDING"
        ),

        // ======= PHASE 3 =======
        SubjectProgress(
            id = "03-01",
            phase = 3,
            category = "Tech",
            name = "Fundamentos de Programación (Python)",
            description = "Python es el lenguaje más versátil del mundo. No aprenderás a ser programador — aprenderás a usar código como herramienta de poder.",
            topics = "Variables, tipos de datos, estructuras de control;Funciones, módulos y programación orientada a objetos;Manejo de archivos, APIs y JSON;Automatización de tareas repetitivas;Web scraping básico con BeautifulSoup;Proyectos reales: bots, scripts de productividad",
            durationText = "6 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "03-02",
            phase = 3,
            category = "Tech",
            name = "Inteligencia Artificial Aplicada",
            description = "No necesitas construir IA — necesitas dominarla. Aprenderás a usar modelos de lenguaje, visión y agentes como extensiones de tu poder cognitivo.",
            topics = "Cómo funcionan los LLMs (sin matemáticas — conceptualmente);Prompt engineering avanzado: técnicas y patrones;Construcción de agentes de IA con herramientas;IA para negocios: casos de uso reales y ROI;APIs de OpenAI, Anthropic, y modelos open source;Flujos de trabajo de IA con n8n o Make",
            durationText = "5 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "03-03",
            phase = 3,
            category = "Tech",
            name = "Automatización y No-Code / Low-Code",
            description = "El empresario moderno no hace trabajo manual repetitivo — lo automatiza. Estas herramientas multiplican tu output sin multiplicar tu tiempo.",
            topics = "Make (ex-Integromat) y Zapier — flujos complejos;Notion como sistema operativo empresarial;Airtable para bases de datos sin código;Webflow o Framer para webs sin programar;Automatización de email marketing (ActiveCampaign);Construcción de dashboards con Looker Studio",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "03-04",
            phase = 3,
            category = "Tech",
            name = "Análisis de Datos y Pensamiento Cuantitativo",
            description = "Los datos son el nuevo petróleo — pero solo para quien sabe refinarlos. Aprenderás a leer números y convertirlos en decisiones.",
            topics = "Estadística descriptiva e inferencial básica;Excel / Google Sheets avanzado para análisis;SQL básico para consultar bases de datos;Visualización de datos: principios de Tufte;KPIs: cómo definir y medir lo correcto;Pandas en Python para análisis de datos reales",
            durationText = "5 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "03-05",
            phase = 3,
            category = "Tech",
            name = "Ciberseguridad y Privacidad Digital",
            description = "En el mundo digital, quien controla la información tiene el poder. Esta materia te protege y te da ventaja estratégica.",
            topics = "Amenazas reales: phishing, OSINT, ingeniería social;Gestores de contraseñas y autenticación 2FA;VPN, Tor y privacidad de red;Seguridad de dispositivos y data personal;OSINT básico: cómo investigar personas y empresas;Criptografía básica aplicada (sin matemáticas profundas)",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "03-06",
            phase = 3,
            category = "Tech",
            name = "Marketing Digital y SEO Técnico",
            description = "Sin visibilidad no hay negocio. Aprenderás a posicionar, atraer y convertir clientes en el ecosistema digital con precisión técnica.",
            topics = "SEO on-page, off-page y técnico;Google Search Console y Analytics 4;Publicidad pagada: Meta Ads y Google Ads;Email marketing y automatización de secuencias;Funnel de ventas digital — diseño y optimización;Copywriting para conversión (diferente de comunicación)",
            durationText = "5 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "03-07",
            phase = 3,
            category = "Tech",
            name = "Diseño Visual y Producción de Contenido",
            description = "El contenido visual es el idioma del poder en el siglo XXI. No necesitas ser artista — necesitas dominar las herramientas que comunican tu visión.",
            topics = "Principios de diseño: composición, tipografía, color;Figma para diseño de interfaces y presentaciones;Canva Pro — producción rápida de contenido;Edición de video básica (CapCut, DaVinci Resolve);Fotografía y visual storytelling básico;Identidad de marca: cómo construir una imagen consistente",
            durationText = "4 semanas",
            status = "PENDING"
        ),

        // ======= PHASE 4 =======
        SubjectProgress(
            id = "04-01",
            phase = 4,
            category = "Negocios",
            name = "Fundamentos de Economía Real",
            description = "No la economía del aula — la economía de cómo funcionan los mercados, los precios y las decisiones humanas en el mundo real.",
            topics = "Oferta, demanda y equilibrio — más allá del gráfico;Incentivos como motor de todo comportamiento humano;Economía conductual (Kahneman, Thaler) — errores sistemáticos;Macroeconomía: inflación, tasas de interés, ciclos económicos;Cómo leer un banco central y su impacto en negocios",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "04-02",
            phase = 4,
            category = "Negocios",
            name = "Finanzas Personales Avanzadas",
            description = "Ganar dinero sin saber administrarlo es un camino al fracaso. Esta materia cubre desde el presupuesto hasta la construcción de riqueza generacional.",
            topics = "Sistema de cuentas y flujo de caja personal;Deuda buena vs. mala — cómo usarla como palanca;Impuestos: estructura legal y minimización fiscal legítima;Fondo de emergencia, liquidez y reservas estratégicas;Libertad financiera: cómo calcular tu número;Psicología del dinero (Morgan Housel) — aplicada",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "04-03",
            phase = 4,
            category = "Negocios",
            name = "Inversión y Mercados Financieros",
            description = "El dinero que no trabaja se erosiona. Aprenderás los principios de la inversión inteligente — no especulación, sino construcción de patrimonio.",
            topics = "Bolsa de valores: cómo funciona realmente;Fondos índice, ETFs y estrategia de largo plazo;Análisis fundamental básico de empresas;Bienes raíces: lógica de inversión y apalancamiento;Criptomonedas: tecnología, riesgo y posición estratégica;Diversificación y gestión de riesgo en portafolios",
            durationText = "5 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "04-04",
            phase = 4,
            category = "Negocios",
            name = "Construcción y Validación de Negocios",
            description = "La mayoría de negocios fracasan porque nunca validaron si alguien quería lo que vendían. Este framework elimina ese error antes de gastar un peso.",
            topics = "Lean Startup: hipótesis, MVP y ciclo build-measure-learn;Jobs to be Done — entender qué compra realmente el cliente;Investigación de mercado real (sin encuestas inútiles);Modelo de negocio Canvas — diseño y validación;Pricing: psicología y estrategia de precios;Product-market fit: cómo saberlo con certeza",
            durationText = "5 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "04-05",
            phase = 4,
            category = "Negocios",
            name = "Ventas de Alto Valor",
            description = "Vender no es manipular — es el arte de conectar una solución real con una necesidad real. El que sabe vender nunca pasa hambre.",
            topics = "El proceso de venta consultiva B2B y B2C;Manejo de objeciones con el método Sandler;Creación de propuestas de valor irresistibles;Cold outreach efectivo: email, LinkedIn, llamadas;Cierre de ventas: técnicas sin presión y con confianza;Follow-up y gestión del pipeline de clientes (CRM)",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "04-06",
            phase = 4,
            category = "Negocios",
            name = "Estrategia Empresarial",
            description = "Por qué algunos negocios dominan su industria por décadas y otros desaparecen en años. Esta materia responde esa pregunta con frameworks aplicables.",
            topics = "5 fuerzas de Porter: análisis competitivo profundo;Ventaja competitiva sostenible — moats y fosos;Blue Ocean Strategy — crear mercados sin competencia;Estrategia de crecimiento: expansión, escala y nicho;Análisis FODA avanzado con acción concreta;Storytelling estratégico para inversores y socios",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "04-07",
            phase = 4,
            category = "Negocios",
            name = "Contabilidad y Finanzas Empresariales",
            description = "No puedes gestionar lo que no entiendes. Un emprendedor que no lee sus números es un piloto con los ojos cerrados.",
            topics = "Balance general, estado de resultados y flujo de caja;Punto de equilibrio y margen de contribución;Métricas SaaS / ecommerce: LTV, CAC, churn rate;Estructura de costos y análisis de rentabilidad;Cuándo y cómo buscar financiamiento externo",
            durationText = "3 semanas",
            status = "PENDING"
        ),

        // ======= PHASE 5 =======
        SubjectProgress(
            id = "05-01",
            phase = 5,
            category = "Comunicación",
            name = "Escritura de Alta Precisión",
            description = "Escribir bien es pensar bien en público. La claridad en la escritura es claridad en el pensamiento — y quien escribe con precisión, lidera.",
            topics = "Pirámide de Minto: estructurar argumentos desde la conclusión;Escritura sin relleno: cada palabra debe ganar su lugar;Emails y documentos ejecutivos de alto impacto;Ensayos persuasivos: estructura, evidencia y conclusión;Escritura para redes sociales con autoridad y alcance;Edición y revisión: el proceso profesional completo",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "05-02",
            phase = 5,
            category = "Comunicación",
            name = "Oratoria y Presentación Ejecutiva",
            description = "Hablar en público con poder no es un talento — es una habilidad técnica. Aprenderás a hablar ante cualquier audiencia con autoridad y presencia.",
            topics = "Gestión del nerviosismo: técnicas fisiológicas y cognitivas;Estructura de discursos de alto impacto;Lenguaje corporal y uso del espacio;Storytelling oral: el arte de la narrativa en vivo;Presentaciones con PowerPoint/Slides de nivel ejecutivo;Improvisación y manejo de preguntas difíciles",
            durationText = "5 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "05-03",
            phase = 5,
            category = "Comunicación",
            name = "Negociación Avanzada",
            description = "Toda interacción humana es una negociación. Quien domina este arte obtiene mejores contratos, mejores relaciones y mejores resultados en todo.",
            topics = "Método Harvard de negociación basada en principios;Tactical empathy (Chris Voss — ex-FBI);BATNA: siempre saber cuál es tu alternativa;El silencio como herramienta de poder;Negociación salarial, de contratos y de precios;Cómo detectar y neutralizar tácticas de manipulación",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "05-04",
            phase = 5,
            category = "Comunicación",
            name = "Psicología Social e Influencia",
            description = "Entender por qué las personas hacen lo que hacen te da una ventaja enorme. Esta materia cubre los mecanismos profundos del comportamiento humano en grupo.",
            topics = "Los 6 principios de influencia de Cialdini — aplicados;Disonancia cognitiva y cómo usarla éticamente;Marcos mentales: cómo encuadrar ideas para que persuadan;Efecto halo, conformidad social y pensamiento grupal;Construcción de confianza y autoridad percibida;Detección de manipulación y protección psicológica",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "05-05",
            phase = 5,
            category = "Comunicación",
            name = "Liderazgo y Gestión de Personas",
            description = "Liderar no es tener un título — es hacer que otros quieran seguirte. Esta materia cubre el liderazgo desde la psicología hasta la operación diaria.",
            topics = "Modelos de liderazgo situacional (Hersey & Blanchard);Cómo dar feedback que mejora sin destruir;Delegación efectiva: el arte de no hacerlo todo solo;Construución de cultura de equipo de alto rendimiento;Resolución de conflictos en equipos;Liderazgo en crisis: cómo actuar cuando todo falla",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "05-06",
            phase = 5,
            category = "Comunicación",
            name = "Construcción de Marca Personal",
            description = "En la economía de la atención, tu reputación es tu activo más valioso. Aprenderás a construir autoridad, presencia y reconocimiento de forma estratégica.",
            topics = "Posicionamiento personal: definir tu lugar único;Estrategia de contenido en LinkedIn, X y YouTube;Cómo construir una audiencia desde cero;El poder del nicho: profundidad vs. amplitud;Consistencia de voz y mensaje a través de plataformas;Networking estratégico: calidad sobre cantidad",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "05-07",
            phase = 5,
            category = "Comunicación",
            name = "Inglés Profesional de Alto Nivel",
            description = "El inglés no es un idioma extra — es el acceso al 80% del conocimiento, los negocios y las oportunidades globales. Esta materia lo aborda como herramienta de poder.",
            topics = "Escritura profesional en inglés: emails, propuestas, reportes;Vocabulario de negocios, tecnología y finanzas;Comprensión auditiva de aceleración: podcasts y conferencias;Presentaciones y negociación en inglés;Inmersión estructurada: método de adquisición acelerada",
            durationText = "Práctica diaria permanente",
            status = "PENDING"
        ),

        // ======= PHASE 6 =======
        SubjectProgress(
            id = "06-01",
            phase = 6,
            category = "Maestría",
            name = "Geopolítica y Poder Global",
            description = "El mundo no es aleatorio — está estructurado por intereses, recursos y poder. Entender la geopolítica es entender las fuerzas que dan forma a cada oportunidad y riesgo.",
            topics = "Teoría realista de las relaciones internacionales;Geografía estratégica: por qué los países hacen lo que hacen;Poder blando y blando — instrumentos de influencia global;Economía política: cómo el dinero mueve naciones;Análisis de escenarios futuros a 10-20 años",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "06-02",
            phase = 6,
            category = "Maestría",
            name = "Historia del Pensamiento Empresarial",
            description = "Los grandes negocios del pasado contienen los principios del futuro. Esta materia destila lecciones de Rockefeller, Carnegie, Ford, Jobs y Bezos en marcos accionables.",
            topics = "Biografías estratégicas: qué hicieron diferente los gigantes;Errores históricos de negocios y sus causas profundas;Cómo las industrias nacen, maduran y mueren;Disrupciones históricas: patrones que se repiten;Filosofía empresarial de largo plazo (Buffett, Munger)",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "06-03",
            phase = 6,
            category = "Maestría",
            name = "Filosofía del Poder y Ética",
            description = "El poder sin ética destruye a quien lo ejerce. Esta materia examina qué es el poder, cómo se obtiene legítimamente y cómo usarlo sin corromperse.",
            topics = "Maquiavelo, Nietzsche y Foucault — lectura crítica del poder;Las 48 leyes del poder — análisis crítico ético;Ética consecuencialista vs deológica en decisiones reales;Legado: qué quieres que digan de ti en 50 años;Responsabilidad social de quien tiene poder real",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "06-04",
            phase = 6,
            category = "Maestría",
            name = "Futurismo Aplicado y Tendencias Tecnológicas",
            description = "Los que ven el futuro con claridad anticipan, en lugar de reaccionar. Aprenderás a analizar tendencias emergentes y posicionarte antes que la mayoría.",
            topics = "Metodologías de forecasting tecnológico;Tendencias convergentes: IA, biotech, energía, espacio;Horizonte de tiempo y curvas S de adopción tecnológica;Cómo identificar oportunidades en mercados emergentes;Construcción de tesis de inversión en el futuro",
            durationText = "3 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "06-05",
            phase = 6,
            category = "Maestría",
            name = "Escalar Sistemas y Equipos",
            description = "Lo que funciona con 3 personas no funciona con 30 ni con 300. La escala requiere rediseñar todo. Esta materia enseña cómo construir organizaciones que crecen sin romperse.",
            topics = "Diseño organizacional: estructuras planas vs. jerárquicas;Sistemas de reclutamiento y selección de talento A+;Cultura organizacional como ventaja competitiva;Procesos y SOPs: documentar para escalar;OKRs a nivel empresa: alineación de 10 a 1000 personas",
            durationText = "4 semanas",
            status = "PENDING"
        ),
        SubjectProgress(
            id = "06-06",
            phase = 6,
            category = "Maestría",
            name = "Síntesis Interdisciplinaria y Sistema Propio",
            description = "El nivel más alto: integrar todo lo aprendido en un sistema personal de pensamiento, acción y creación de valor único. Tu filosofía operativa personal.",
            topics = "Construcción de tu framework personal de decisiones;Síntesis de disciplinas: encontrar patrones entre campos;Definición de tu tesis de vida y misión de 10 años;Diseño de tu portafolio de proyectos y legado;Mentoría y enseñanza como forma de maestría avanzada;Revisión anual y actualización del sistema",
            durationText = "Permanente",
            status = "PENDING"
        )
    )
}
