document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("examForm");
    const answeredCount = document.getElementById("answered-count");
    const timerEl = document.getElementById("time-left");
    const questionLinks = document.querySelectorAll(".question-list a");
    const questionBlocks = document.querySelectorAll(".question-block");
    let remaining = examDurationMinutes * 60;

    const timer = setInterval(() => {
        const m = String(Math.floor(remaining / 60)).padStart(2, "0");
        const s = String(remaining % 60).padStart(2, "0");
        timerEl.textContent = `${m}分${s}秒`;
        remaining--;
        if (remaining < 0) {
            clearInterval(timer);
            form.submit();
        }
    }, 1000);

    const updateAnswered = () => {
        let count = 0;
        questionBlocks.forEach((block, idx) => {
            const inputs = block.querySelectorAll("input, textarea");
            let answered = false;

            inputs.forEach(el => {
                if ((el.type === "radio" && el.checked) ||
                    (el.type === "checkbox" && el.checked) ||
                    (el.tagName === "TEXTAREA" && el.value.trim() !== "")) {
                    answered = true;
                }
            });

            const link = questionLinks[idx];
            if (answered) {
                count++;
                link.classList.add("answered");
            } else {
                link.classList.remove("answered");
            }
        });

        answeredCount.textContent = count;
    };

    form.addEventListener("change", updateAnswered);
    form.addEventListener("input", updateAnswered);

    window.onbeforeunload = function (e) {
        e.preventDefault();
        return "試験中にページを離れると提出されません。";
    };

    window.addEventListener("keydown", function (e) {
        if (
            e.key === "F5" ||
            (e.ctrlKey && ["r", "R", "n", "N", "t", "T"].includes(e.key))
        ) {
            e.preventDefault();
            alert("試験中にこの操作はできません。");
        }
    });

    document.addEventListener("contextmenu", e => e.preventDefault());

    const originalWidth = window.innerWidth;
    const originalHeight = window.innerHeight;
    window.addEventListener("resize", () => {
        if (
            Math.abs(window.innerWidth - originalWidth) > 50 ||
            Math.abs(window.innerHeight - originalHeight) > 50
        ) {
            alert("画面サイズの変更は禁止されています。");
            window.resizeTo(originalWidth, originalHeight);
        }
    });

    document.addEventListener("copy", e => e.preventDefault());
    document.addEventListener("cut", e => e.preventDefault());
    document.addEventListener("dragstart", e => e.preventDefault());
});

function scrollToQuestion(index) {
    const target = document.getElementById("question-" + index);
    if (target) {
        target.scrollIntoView({behavior: "smooth", block: "start"});
    }
}

function submitExam() {
    if (confirm("本当に提出しますか？")) {
        const form = document.getElementById("examForm");
        form.submit();
        setTimeout(() => window.close(), 1500);
    }
}
