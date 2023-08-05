export type Job = {
    jobId: string,
    poster: {
        hrSpecialistId: string,
        username: string
    },
    technicalSkills: {
        technicalSkill: string
    }[],
    personalSkills: {
        personalSkill: string

    }[],
    title: string,
    until: Date,
    status: string,
    jobDescription: string
}